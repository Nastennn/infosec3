import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        // Считывем ключ из файла.
        String userKey = Reader.readText("src/key");
        System.out.println("Key is: " + userKey);

        // Считываем текст для кодирования.
        String textFromFile = Reader.readText("src/test");
        System.out.println("Original text: " + textFromFile);

        // Получаем из строчек массивы байт.
        byte[] key = userKey.getBytes();
        byte[] text = textFromFile.getBytes(StandardCharsets.UTF_8);

        // Шифруем текст.
        byte[] encrypted = RC4.encrypt(text, key);
        // Переводим шифрованный текст в строку и выводим.
        String encryptedText = RC4.convertToString(encrypted);
        System.out.println("Encrypted: " + encryptedText);
        // Дешифруем текст.
        byte[] decrypted = RC4.decrypt(encrypted, key);
        // Переводим расшифрованный текст в строку и выводим.
        String decryptedText = RC4.convertToString(decrypted);
        System.out.println("Decrypted: " + decryptedText);


    }

}
