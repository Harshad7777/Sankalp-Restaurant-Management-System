package com.restaurant.service.payment;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QRCodeGeneratorService {

    public String generateUPIString(String vpa, String payeeName, BigDecimal amount, String billNo) {
        // Standard NPCI UPI URI scheme: upi://pay?pa=...&pn=...&am=...&tn=...
        return String.format(
                "upi://pay?pa=%s&pn=%s&am=%s&cu=INR&tn=Bill%%20Payment%%20%s",
                vpa != null ? vpa : "sankalprestaurant@upi",
                payeeName != null ? payeeName.replace(" ", "%20") : "Sankalp%20Restaurant",
                amount.setScale(2, java.math.RoundingMode.HALF_UP).toString(),
                billNo
        );
    }

    public String generateQRCodeBase64(String content, int width, int height) throws Exception {
        QRCodeWriter writer = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);

        BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        return "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
}
