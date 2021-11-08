import java.nio.charset.StandardCharsets;

public class RC4 {
    public static byte[] encrypt(byte[] plaintext, byte[] key) {

        // Инициализация S-блока.
        byte[] ksa = getKeyScheduling(key);
        // Генерация псевдослучайного слова.
        byte[] prga = getPRGA(plaintext, ksa);

        // Проводим операцию XOR между текстом и псевдослучайным словом.
        byte[] result = new byte[plaintext.length];
        for(int i=0; i<result.length; i++) {
            result[i] = (byte) (plaintext[i] ^ prga[i]);
        }
        return result;
    }

    // Метод дешифрации.
    public static byte[] decrypt(byte[] encrypted, byte[] key) {
        return encrypt(encrypted, key); 
    }

    // Инициализация S-блока.
    private static byte[] getKeyScheduling(byte[] key) {
        // По заданию размер блока 16. Это означает, что элементов в S-блоке
        // должно быть 65536.
        // Инициализируем массив размеров 65536.
        byte[] s = new byte[65536];
        // Заполняем массив.
        for(int i=0; i<65536; i++) {
            s[i] = (byte) i;
        }

        int j = 0;
        // Перемешиваем массив.
        for(int i=0; i<65536; i++) {
            j = (j + s[i] + key[i % key.length]) & 0xFFFF;
            byte temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }

        return s;
    }

    // Генерация псевдослучайного слова K.
    private static byte[] getPRGA (byte[] text, byte[] ksa) {

        int i = 0;
        int j = 0;

        byte[] result = new byte[text.length];

        for(int p=0; p<result.length; p++) {
            // Цикл генерации.
            i = (i + 1) & 0xFFFF;
            j = (j + ksa[i]) & 0xFFFF;
            // Меняем местами переменные.
            byte temp = ksa[i];
            ksa[i] = ksa[j];
            ksa[j] =  temp;
            // Получение псевдослучайного слова.
            byte k = ksa[(ksa[i] + ksa[j]) & 0xFFFF];

            result[p] = k;
        }

        return result;
    }

    // Функция генерации строки из массива байт.
    public static String convertToString(byte[] text) {
        return new String(text, StandardCharsets.UTF_8);
    }
}
