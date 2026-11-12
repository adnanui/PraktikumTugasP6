# Program Pengelola Nilai Siswa (Java)

| | |
|---|---|
| **Nama** | Adnan Aiman Nabil |
| **NIM** | L0325015 |

---

## 1. Deskripsi Program

Program ini adalah aplikasi konsol sederhana berbahasa Java untuk mengelola data nilai siswa. Program membaca data siswa dari keyboard, menyimpannya ke dalam file teks `laporan_nilai.txt`, lalu membuka kembali file tersebut dan menampilkan isinya di konsol sebagai bukti bahwa data berhasil ditulis dan dibaca.

Program ditulis dengan gaya procedural/fungsional: tidak ada objek `Siswa`, semua logika dipecah menjadi method `static` dengan tugas masing-masing.

## 2. Tujuan

- Menerapkan input konsol menggunakan `Scanner`.
- Menerapkan penulisan file teks menggunakan `FileWriter` dan `BufferedWriter`.
- Menerapkan pembacaan file teks menggunakan `FileReader` dan `BufferedReader`.
- Menerapkan penanganan kesalahan (`IOException`) dan pengelolaan resource dengan `try-with-resources`.

## 3. Lingkungan Pengembangan

- Bahasa: Java (JDK 8 atau lebih baru)
- IDE: Apache NetBeans
- File: `NilaiSiswa.java` (package `nilaisiswa`)

## 4. Struktur Program

| Method | Fungsi |
|---|---|
| `main()` | Alur utama: baca jumlah siswa → tulis laporan → tampilkan isi file |
| `bacaJumlahSiswa()` | Meminta jumlah siswa, diulang sampai input berupa bilangan bulat > 0 |
| `bacaNama()` | Meminta nama siswa, diulang jika kosong |
| `bacaNilai()` | Meminta nilai siswa (`double`), diulang jika bukan angka atau di luar 0–100. Koma desimal seperti `65,5` diterima |
| `tentukanStatus()` | Mengembalikan `"Lulus"` jika nilai ≥ 70, selain itu `"Tidak Lulus"` |
| `tulisLaporan()` | Membaca data tiap siswa dalam loop, menentukan status, menulis ke file beserta ringkasan |
| `tampilkanIsiFile()` | Membaca `laporan_nilai.txt` baris demi baris dan mencetaknya ke konsol |

Dua konstanta digunakan agar mudah diubah: `NAMA_FILE = "laporan_nilai.txt"` dan `NILAI_LULUS = 70.0`.

## 5. Alur Program

### Langkah 1 — Input Konsol dan Penulisan File

1. Pengguna memasukkan jumlah siswa.
2. `BufferedWriter` dibuka pada file `laporan_nilai.txt`, lalu header tabel ditulis.
3. Untuk setiap siswa (loop `for`):
   1. Baca nama (`String`) dan nilai (`double`).
   2. Tentukan status langsung di dalam loop: nilai ≥ 70 → **Lulus**, nilai < 70 → **Tidak Lulus**.
   3. Tambahkan nilai ke total dan hitung jumlah lulus / tidak lulus.
   4. Tulis satu baris data siswa ke file.
4. Setelah loop selesai, tulis ringkasan di akhir file:
   - rata-rata nilai kelas (`total / jumlah siswa`)
   - jumlah siswa lulus
   - jumlah siswa tidak lulus

### Langkah 2 — Pembacaan File

Setelah penulisan selesai (writer sudah tertutup), file dibuka lagi dengan `BufferedReader` yang membungkus `FileReader`. Isinya dibaca dengan `readLine()` dalam loop `while` sampai method itu mengembalikan `null` (akhir file), dan setiap baris dicetak ke konsol.

```java
String baris;
while ((baris = reader.readLine()) != null) {
    System.out.println(baris);
}
```

### Langkah 3 — Exception Handling dan Resource Management

Seluruh proses baca/tulis file dibungkus `try-with-resources`:

```java
try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {
    // ... tulis data ...
} catch (IOException e) {
    System.out.println("Terjadi kesalahan saat menulis file: " + e.getMessage());
}
```

Dengan struktur ini, `writer` (dan juga `reader` pada method pembacaan) otomatis ditutup di akhir blok `try`, baik proses berjalan normal maupun terjadi error, sehingga tidak perlu memanggil `close()` secara manual. Jika terjadi `IOException`, program menampilkan pesan kesalahan, bukan berhenti dengan stack trace. Jika penulisan gagal, pembacaan file tidak dijalankan.

## 6. Cara Menjalankan di NetBeans

1. Buka NetBeans, pilih **File → New Project → Java with Ant → Java Application**, lalu klik **Next**.
2. Beri nama project `NilaiSiswa`, pastikan **Create Main Class** tercentang (otomatis menjadi `nilaisiswa.NilaiSiswa`), lalu klik **Finish**.
3. Ganti seluruh isi file `NilaiSiswa.java` dengan kode dari file yang disertakan. Jika baris `package ...;` di paling atas pada project kamu berbeda, pertahankan baris package milik project kamu.
4. Jalankan dengan **F6** (Run Project) atau **Shift+F6** (Run File).
5. Klik di jendela **Output** di bagian bawah, lalu ketik input dan tekan Enter pada setiap pertanyaan.
6. File `laporan_nilai.txt` dibuat di folder utama project. Untuk melihatnya di NetBeans, buka tab **Files** (Ctrl+2), lalu buka folder `NilaiSiswa`.

## 7. Contoh Penggunaan

**Input dan output di konsol:**

```
Masukkan jumlah siswa: 3

Data siswa ke-1
Nama  : Budi Santoso
Nilai : 85

Data siswa ke-2
Nama  : Siti Aminah
Nilai : 65,5

Data siswa ke-3
Nama  : Andi
Nilai : 70

Data berhasil ditulis ke file laporan_nilai.txt

===== ISI FILE laporan_nilai.txt =====
========== LAPORAN NILAI SISWA ==========
No   Nama                      Nilai    Status      
-----------------------------------------------------
1    Budi Santoso              85.00    Lulus       
2    Siti Aminah               65.50    Tidak Lulus 
3    Andi                      70.00    Lulus       
-----------------------------------------------------
RINGKASAN
Rata-rata nilai kelas : 73.50
Jumlah siswa lulus    : 2
Jumlah tidak lulus    : 1
```

**Perhitungan ringkasan:** (85 + 65,5 + 70) / 3 = 73,50. Nilai 70 termasuk **Lulus** karena batasnya adalah ≥ 70.

> **Screenshot:** _(tempel screenshot jendela Output NetBeans dan isi `laporan_nilai.txt` di sini)_

## 8. Penjelasan Konsep yang Digunakan

| Komponen | Peran dalam program |
|---|---|
| `Scanner` | Membaca input dari keyboard (`System.in`) |
| `FileWriter` | Membuka/membuat file teks untuk ditulis (data lama ditimpa) |
| `BufferedWriter` | Membungkus `FileWriter` agar penulisan lebih efisien lewat buffer; `newLine()` menambah baris baru sesuai sistem operasi |
| `FileReader` | Membuka file teks untuk dibaca |
| `BufferedReader` | Membungkus `FileReader`; menyediakan `readLine()` untuk membaca per baris |
| `IOException` | Exception yang mungkin terjadi pada operasi file (file tidak bisa dibuat, tidak ditemukan, dsb.) |
| `try-with-resources` | Menutup stream secara otomatis di akhir blok |
| `String.format` | Merapikan kolom tabel dan membatasi nilai menjadi 2 angka desimal |

## 9. Kesimpulan

Program berhasil membaca data siswa dari konsol, menentukan status kelulusan di dalam loop, menyimpan data beserta ringkasan (rata-rata, jumlah lulus, jumlah tidak lulus) ke `laporan_nilai.txt`, lalu membaca kembali file tersebut dan menampilkannya di konsol. Penggunaan `try-with-resources` memastikan semua stream tertutup dengan benar, dan blok `catch (IOException)` menjaga program tetap berjalan dengan pesan yang jelas ketika terjadi kesalahan file.
