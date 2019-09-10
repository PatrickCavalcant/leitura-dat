package leituraDat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Thread;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Leitura extends Thread {

    private long comeco, termino, tamanho;
    private long num[] = new long[11];

    Leitura(long inicio, long fim, long n) {
        this.tamanho = n;
        this.comeco = inicio;
        this.termino = fim;
    }

    public long[] getSomatorio() {
        return this.num;
    }

    public void run() {
        InputStream is = null;
        FileInputStream fileInputStream = null;
        int c;
        try {
            File file = new File("F:\\Documentos\\ProgramaçãoIII\\Java\\leituraDat\\dados.dat");
            byte[] arrayByte = new byte[(int) file.length()];
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(arrayByte);
            for (byte posicao : arrayByte) {
                c = (char) posicao;
                this.num[posicao]++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(Leitura.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        long tamanho = 0;
        long[] contador = new long[11];
        double[] porcentagem = new double[11];
        long tempoInicio, tempoFim;
        File arquivo = new File("F:\\Documentos\\ProgramaçãoIII\\Java\\leituraDat\\dados.dat");
        tamanho = arquivo.length();
        tempoInicio = System.currentTimeMillis();
        Leitura t1;
        Leitura t2;
        t1 = new Leitura(0, tamanho / 2, tamanho);
        t1.start();
        t2 = new Leitura(tamanho / 2 + 1, tamanho - 1, tamanho);
        t2.start();
        try {
            t1.join();
            t2.join();

        } catch (Exception e) {
            e.printStackTrace();
        }
        tempoFim = System.currentTimeMillis();
        long total = 0;
        for (int i = 0; i < 11; i++) {
            contador[i] = (t1.getSomatorio()[i]);
            total = total + contador[i];
        }
        for (int i = 0; i < 11; i++) {
            double aux = contador[i];
            porcentagem[i] = (aux / total) * 100;
        }
        for (int i = 0; i < 11; i++) {
            System.out.println(i + " Tamanho " + contador[i]);
        }
        for (int i = 0; i < 11; i++) {
            System.out.println(i + " Porcentagem " + porcentagem[i]);
        }
        System.out.println("Tempo de processamento: " + (tempoFim - tempoInicio));
    }
}
