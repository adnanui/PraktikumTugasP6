package nilaisiswa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Program sederhana pengelola nilai siswa (gaya procedural/fungsional).
 *
 * Alur:
 *   1. Baca data siswa dari konsol, tentukan status, tulis ke laporan_nilai.txt
 *   2. Baca kembali laporan_nilai.txt dan tampilkan isinya di konsol
 *   3. Semua operasi file memakai try-with-resources + penanganan IOException
 */
public class NilaiSiswa {

    static final String NAMA_FILE = "laporan_nilai.txt";
    static final double NILAI_LULUS = 70.0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int jumlahSiswa = bacaJumlahSiswa(scanner);

        // Langkah 1: input konsol + tulis file
        boolean berhasilTulis = tulisLaporan(scanner, jumlahSiswa);

        // Langkah 2: baca file + tampilkan ke konsol
        if (berhasilTulis) {
            tampilkanIsiFile();
        }

        scanner.close();
    }

    // ---------------------------------------------------------------
    // Langkah 1: Input Console & Penulisan File
    // ---------------------------------------------------------------

    static boolean tulisLaporan(Scanner scanner, int jumlahSiswa) {
        double totalNilai = 0;
        int jumlahLulus = 0;
        int jumlahTidakLulus = 0;

        // try-with-resources: writer otomatis ditutup (close) di akhir blok
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {

            writer.write("========== LAPORAN NILAI SISWA ==========");
            writer.newLine();
            writer.write(String.format("%-4s %-25s %-8s %-12s", "No", "Nama", "Nilai", "Status"));
            writer.newLine();
            writer.write("-----------------------------------------------------");
            writer.newLine();

            for (int i = 1; i <= jumlahSiswa; i++) {
                System.out.println("\nData siswa ke-" + i);
                String nama = bacaNama(scanner);
                double nilai = bacaNilai(scanner);

                // Status ditentukan langsung di dalam loop
                String status = tentukanStatus(nilai);

                totalNilai += nilai;
                if (nilai >= NILAI_LULUS) {
                    jumlahLulus++;
                } else {
                    jumlahTidakLulus++;
                }

                writer.write(String.format("%-4d %-25s %-8.2f %-12s", i, nama, nilai, status));
                writer.newLine();
            }

            // Ringkasan di akhir file
            double rataRata = totalNilai / jumlahSiswa;
            writer.write("-----------------------------------------------------");
            writer.newLine();
            writer.write("RINGKASAN");
            writer.newLine();
            writer.write(String.format("Rata-rata nilai kelas : %.2f", rataRata));
            writer.newLine();
            writer.write("Jumlah siswa lulus    : " + jumlahLulus);
            writer.newLine();
            writer.write("Jumlah tidak lulus    : " + jumlahTidakLulus);
            writer.newLine();

            System.out.println("\nData berhasil ditulis ke file " + NAMA_FILE);
            return true;

        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menulis file: " + e.getMessage());
            return false;
        }
    }

    static String tentukanStatus(double nilai) {
        return (nilai >= NILAI_LULUS) ? "Lulus" : "Tidak Lulus";
    }

    // ---------------------------------------------------------------
    // Langkah 2 & 3: Pembacaan File + Exception Handling
    // ---------------------------------------------------------------

    static void tampilkanIsiFile() {
        System.out.println("\n===== ISI FILE " + NAMA_FILE + " =====");

        // try-with-resources: reader otomatis ditutup (close) di akhir blok
        try (BufferedReader reader = new BufferedReader(new FileReader(NAMA_FILE))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                System.out.println(baris);
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // Fungsi bantu input (dengan validasi sederhana)
    // ---------------------------------------------------------------

    static int bacaJumlahSiswa(Scanner scanner) {
        while (true) {
            System.out.print("Masukkan jumlah siswa: ");
            String input = scanner.nextLine().trim();
            try {
                int jumlah = Integer.parseInt(input);
                if (jumlah > 0) {
                    return jumlah;
                }
                System.out.println("Jumlah siswa harus lebih dari 0.");
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid, masukkan angka bulat.");
            }
        }
    }

    static String bacaNama(Scanner scanner) {
        while (true) {
            System.out.print("Nama  : ");
            String nama = scanner.nextLine().trim();
            if (!nama.isEmpty()) {
                return nama;
            }
            System.out.println("Nama tidak boleh kosong.");
        }
    }

    static double bacaNilai(Scanner scanner) {
        while (true) {
            System.out.print("Nilai : ");
            // replace koma -> titik supaya "85,5" tetap bisa dibaca
            String input = scanner.nextLine().trim().replace(',', '.');
            try {
                double nilai = Double.parseDouble(input);
                if (nilai >= 0 && nilai <= 100) {
                    return nilai;
                }
                System.out.println("Nilai harus antara 0 sampai 100.");
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid, masukkan angka.");
            }
        }
    }
}