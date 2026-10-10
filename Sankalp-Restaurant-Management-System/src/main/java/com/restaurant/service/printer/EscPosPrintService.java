package com.restaurant.service.printer;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurant.model.Bill;
import com.restaurant.model.OrderDetails;
import com.restaurant.model.Orders;

@Service
public class EscPosPrintService {

    // ESC/POS Commands
    private static final byte[] INIT_PRINTER = new byte[]{0x1B, 0x40};
    private static final byte[] ALIGN_LEFT = new byte[]{0x1B, 0x61, 0x00};
    private static final byte[] ALIGN_CENTER = new byte[]{0x1B, 0x61, 0x01};
    private static final byte[] ALIGN_RIGHT = new byte[]{0x1B, 0x61, 0x02};
    private static final byte[] BOLD_ON = new byte[]{0x1B, 0x45, 0x01};
    private static final byte[] BOLD_OFF = new byte[]{0x1B, 0x45, 0x00};
    private static final byte[] DOUBLE_HEIGHT_ON = new byte[]{0x1D, 0x21, 0x10};
    private static final byte[] DOUBLE_SIZE_ON = new byte[]{0x1D, 0x21, 0x11};
    private static final byte[] NORMAL_SIZE = new byte[]{0x1D, 0x21, 0x00};
    private static final byte[] CUT_PAPER = new byte[]{0x1D, 0x56, 0x00};
    private static final byte[] OPEN_DRAWER = new byte[]{0x1B, 0x70, 0x00, 0x19, (byte) 0xFA};

    public byte[] generateKOTBytes(Orders order, List<OrderDetails> details, String tableNumber) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        out.write(INIT_PRINTER);
        out.write(ALIGN_CENTER);
        out.write(BOLD_ON);
        out.write(DOUBLE_SIZE_ON);
        out.write("--- KITCHEN ORDER TICKET ---\n".getBytes(StandardCharsets.UTF_8));
        out.write(NORMAL_SIZE);
        out.write(BOLD_OFF);

        out.write(ALIGN_LEFT);
        out.write(("Order No : " + order.getOrderNo() + "\n").getBytes(StandardCharsets.UTF_8));
        out.write(("Table    : " + (tableNumber != null ? tableNumber : order.getTableId()) + "\n").getBytes(StandardCharsets.UTF_8));
        out.write(("Type     : " + (order.getOrderType() != null ? order.getOrderType() : "DINE_IN") + "\n").getBytes(StandardCharsets.UTF_8));
        out.write(("Time     : " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + "\n").getBytes(StandardCharsets.UTF_8));
        out.write("------------------------------------------------\n".getBytes(StandardCharsets.UTF_8));

        out.write(BOLD_ON);
        out.write(String.format("%-28s %-6s\n", "ITEM NAME", "QTY").getBytes(StandardCharsets.UTF_8));
        out.write(BOLD_OFF);
        out.write("------------------------------------------------\n".getBytes(StandardCharsets.UTF_8));

        if (details != null) {
            for (OrderDetails item : details) {
                String name = "Item #" + item.getItemId();
                out.write(String.format("%-28s %-6d\n", name, item.getQuantity()).getBytes(StandardCharsets.UTF_8));
                if (item.getKitchenNote() != null && !item.getKitchenNote().isBlank()) {
                    out.write(("   Note: " + item.getKitchenNote() + "\n").getBytes(StandardCharsets.UTF_8));
                }
            }
        }

        out.write("------------------------------------------------\n\n\n".getBytes(StandardCharsets.UTF_8));
        out.write(CUT_PAPER);

        return out.toByteArray();
    }

    public byte[] generateBillBytes(Bill bill, Orders order, List<OrderDetails> details, String restaurantName) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        out.write(INIT_PRINTER);
        out.write(ALIGN_CENTER);
        out.write(BOLD_ON);
        out.write(DOUBLE_SIZE_ON);
        out.write(((restaurantName != null ? restaurantName : "SANKALP RESTAURANT") + "\n").getBytes(StandardCharsets.UTF_8));
        out.write(NORMAL_SIZE);
        out.write(BOLD_OFF);
        out.write("Authentic Flavors & Fine Dining\n".getBytes(StandardCharsets.UTF_8));
        out.write("GSTIN: 24ABCDE1234F1Z5 | FSSAI: 10019021004567\n".getBytes(StandardCharsets.UTF_8));
        out.write("------------------------------------------------\n".getBytes(StandardCharsets.UTF_8));

        out.write(ALIGN_LEFT);
        out.write(("Bill No   : " + bill.getBillNo() + "\n").getBytes(StandardCharsets.UTF_8));
        out.write(("Bill Date : " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + "\n").getBytes(StandardCharsets.UTF_8));
        out.write(("Payment   : " + bill.getPaymentMethod() + " (" + bill.getPaymentStatus() + ")\n").getBytes(StandardCharsets.UTF_8));
        out.write("------------------------------------------------\n".getBytes(StandardCharsets.UTF_8));

        out.write(BOLD_ON);
        out.write(String.format("%-20s %-4s %-8s %-8s\n", "ITEM", "QTY", "PRICE", "TOTAL").getBytes(StandardCharsets.UTF_8));
        out.write(BOLD_OFF);
        out.write("------------------------------------------------\n".getBytes(StandardCharsets.UTF_8));

        if (details != null) {
            for (OrderDetails item : details) {
                out.write(String.format("%-20s %-4d %-8.2f %-8.2f\n",
                        "Item #" + item.getItemId(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getSubtotal()
                ).getBytes(StandardCharsets.UTF_8));
            }
        }

        out.write("------------------------------------------------\n".getBytes(StandardCharsets.UTF_8));
        out.write(ALIGN_RIGHT);
        out.write(String.format("Subtotal  : ₹%.2f\n", bill.getSubtotal()).getBytes(StandardCharsets.UTF_8));
        out.write(String.format("GST (5%%)  : ₹%.2f\n", bill.getGst()).getBytes(StandardCharsets.UTF_8));
        out.write(String.format("Discount  : ₹%.2f\n", bill.getDiscount()).getBytes(StandardCharsets.UTF_8));
        out.write(BOLD_ON);
        out.write(DOUBLE_HEIGHT_ON);
        out.write(String.format("GRAND TOTAL: ₹%.2f\n", bill.getGrandTotal()).getBytes(StandardCharsets.UTF_8));
        out.write(NORMAL_SIZE);
        out.write(BOLD_OFF);

        out.write(ALIGN_CENTER);
        out.write("\nThank you for dining with us!\n".getBytes(StandardCharsets.UTF_8));
        out.write("Please visit again.\n\n\n".getBytes(StandardCharsets.UTF_8));

        out.write(OPEN_DRAWER);
        out.write(CUT_PAPER);

        return out.toByteArray();
    }

    public boolean sendBytesToNetworkPrinter(String ipAddress, int port, byte[] data) {
        try (Socket socket = new Socket(ipAddress, port);
             OutputStream os = socket.getOutputStream()) {
            os.write(data);
            os.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String encodeBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}
