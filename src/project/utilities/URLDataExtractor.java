package project.utilities;

import project.rsa.PublicKey;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLDataExtractor {

    private String dataString;
    private PublicKey publicKey;
    private List<Integer> codedValues;

    public URLDataExtractor(String urlAddressString) {
        this.dataString = receiveFileDataString(urlAddressString);
        this.publicKey = extractPublicKey();
        this.codedValues = extractCodedValues();
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public List<Integer> getCodedValues() {
        return codedValues;
    }

    private String receiveFileDataString(String urlAddressString) {
        try (Scanner scanner = new Scanner(new URL(urlAddressString).openStream())) {
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
                stringBuilder.append('\n');
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private PublicKey extractPublicKey() {
        Matcher matcher = Pattern.compile("\\((.*?)\\)").matcher(dataString);

        String publicKeyValuesString = "";
        while (matcher.find()) {
            publicKeyValuesString = matcher.group(1);
        }
        // Right now we have in the publicKeyValuesString two values separated by comma
        String[] publicKeyValues = publicKeyValuesString.split(",");
        // Trim used to get rid of whitespace characters
        int valueN = Integer.parseInt(publicKeyValues[0].trim());
        int valueE = Integer.parseInt(publicKeyValues[1].trim());

        return new PublicKey(valueN, valueE);
    }

    private List<Integer> extractCodedValues() {
        List<Integer> codedValues = new ArrayList<>();

        // After ':' character are located coded values
        Scanner scanner = new Scanner(this.dataString.substring(this.dataString.indexOf(':') + 1));
        while (scanner.hasNextInt()) {
            codedValues.add(scanner.nextInt());
        }
        return codedValues;
    }
}