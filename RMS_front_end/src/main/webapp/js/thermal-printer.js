/**
 * WebBluetooth Thermal Receipt Printer Driver for Sankalp RMS
 * Connects directly to ESC/POS Bluetooth receipt printers from browser.
 */

window.ThermalPrinter = {
    bluetoothDevice: null,
    gattCharacteristic: null,

    /**
     * Request pairing with a Bluetooth Thermal Printer
     */
    async connectBluetoothPrinter() {
        try {
            console.log("Requesting Bluetooth Thermal Printer device...");
            this.bluetoothDevice = await navigator.bluetooth.requestDevice({
                acceptAllDevices: true,
                optionalServices: [
                    '000018f0-0000-1000-8000-00805f9b34fb', // Standard ESC/POS printer service UUID
                    'e7810a71-73ae-499d-8c15-faa9aef0c3f2'
                ]
            });

            console.log("Connecting to GATT Server on " + this.bluetoothDevice.name);
            const server = await this.bluetoothDevice.gatt.connect();

            // Discover services
            const services = await server.getPrimaryServices();
            if (services.length === 0) {
                throw new Error("No GATT services found on printer");
            }

            // Find writable characteristic
            for (const service of services) {
                const characteristics = await service.getCharacteristics();
                for (const char of characteristics) {
                    if (char.properties.write || char.properties.writeWithoutResponse) {
                        this.gattCharacteristic = char;
                        console.log("Connected to Printer Characteristic:", char.uuid);
                        return true;
                    }
                }
            }

            throw new Error("No writable ESC/POS characteristic found on Bluetooth printer.");
        } catch (error) {
            console.error("Bluetooth printer connection error:", error);
            alert("Bluetooth Printer Connection Failed: " + error.message);
            return false;
        }
    },

    /**
     * Print ESC/POS Base64 Payload received from API
     */
    async printBase64ESCPOSToBluetooth(base64Data) {
        if (!this.gattCharacteristic) {
            const connected = await this.connectBluetoothPrinter();
            if (!connected) return false;
        }

        try {
            // Convert base64 string to Uint8Array
            const binaryString = atob(base64Data);
            const len = binaryString.length;
            const bytes = new Uint8Array(len);
            for (let i = 0; i < len; i++) {
                bytes[i] = binaryString.charCodeAt(i);
            }

            // Chunk bytes (Bluetooth GATT MTU limit is typically 20-512 bytes)
            const chunkSize = 100;
            for (let i = 0; i < bytes.length; i += chunkSize) {
                const chunk = bytes.slice(i, i + chunkSize);
                if (this.gattCharacteristic.properties.writeWithoutResponse) {
                    await this.gattCharacteristic.writeValueWithoutResponse(chunk);
                } else {
                    await this.gattCharacteristic.writeValue(chunk);
                }
            }

            console.log("ESC/POS payload successfully sent to Bluetooth printer!");
            return true;
        } catch (error) {
            console.error("Failed to print payload over Bluetooth:", error);
            alert("Bluetooth Print Error: " + error.message);
            return false;
        }
    },

    /**
     * Trigger API Print KOT & Send to Bluetooth Printer
     */
    async printKOT(orderId, printerIp = "") {
        try {
            const response = await fetch(`/api/printer/print-kot/${orderId}?printerIp=${encodeURIComponent(printerIp)}`, {
                method: 'POST'
            });
            const result = await response.json();

            if (result.success && result.escposBase64) {
                await this.printBase64ESCPOSToBluetooth(result.escposBase64);
            } else {
                alert(result.message || "Failed to generate KOT print payload.");
            }
        } catch (e) {
            console.error("Error printing KOT:", e);
        }
    },

    /**
     * Trigger API Print Bill & Send to Bluetooth Printer
     */
    async printBill(billId, printerIp = "") {
        try {
            const response = await fetch(`/api/printer/print-bill/${billId}?printerIp=${encodeURIComponent(printerIp)}`, {
                method: 'POST'
            });
            const result = await response.json();

            if (result.success && result.escposBase64) {
                await this.printBase64ESCPOSToBluetooth(result.escposBase64);
            } else {
                alert(result.message || "Failed to generate Bill print payload.");
            }
        } catch (e) {
            console.error("Error printing Bill:", e);
        }
    }
};
