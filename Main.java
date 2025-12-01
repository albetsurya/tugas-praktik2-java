import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);

    static Menu[] menu = new Menu[50];
    static int jumlahMenu = 0;

    static String[] pesananNama = new String[50];
    static int[] pesananJumlah = new int[50];
    static int jumlahPesanan = 0;

    public static void main(String[] args) {

        tambahMenuDefault();

        while (true) {
            System.out.println("\n===== MENU UTAMA =====");
            System.out.println("1. Pelanggan (Pemesanan)");
            System.out.println("2. Pemilik (Manajemen Menu)");
            System.out.println("3. Keluar");
            System.out.print("Pilih: ");
            int pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1 -> menuPelanggan();
                case 2 -> menuPemilik();
                case 3 -> {
                    System.out.println("Terima kasih!");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    static void tambahMenuDefault() {
        menu[jumlahMenu++] = new Menu("Nasi Goreng", 25000, "makanan");
        menu[jumlahMenu++] = new Menu("Ayam Bakar", 30000, "makanan");
        menu[jumlahMenu++] = new Menu("Sate Ayam", 28000, "makanan");
        menu[jumlahMenu++] = new Menu("Mie Ayam", 20000, "makanan");

        menu[jumlahMenu++] = new Menu("Es Teh", 8000, "minuman");
        menu[jumlahMenu++] = new Menu("Es Jeruk", 10000, "minuman");
        menu[jumlahMenu++] = new Menu("Jus Alpukat", 15000, "minuman");
        menu[jumlahMenu++] = new Menu("Kopi Hitam", 12000, "minuman");
    }

    static void menuPelanggan() {
        jumlahPesanan = 0;

        while (true) {
            tampilkanMenu();

            System.out.print("Masukkan nama menu (atau 'selesai'): ");
            String nama = input.nextLine();

            if (nama.equalsIgnoreCase("selesai")) {
                break;
            }

            Menu m = cariMenu(nama);
            if (m == null) {
                System.out.println("Menu tidak ditemukan, coba lagi!");
                continue;
            }

            System.out.print("Jumlah: ");
            int jml = input.nextInt();
            input.nextLine();

            pesananNama[jumlahPesanan] = m.getNama();
            pesananJumlah[jumlahPesanan] = jml;
            jumlahPesanan++;

            System.out.println("Pesanan ditambahkan!");
        }

        cetakStruk();
    }

    static void tampilkanMenu() {
        System.out.println("\n--- MENU MAKANAN ---");
        for (int i = 0; i < jumlahMenu; i++) {
            if (menu[i].getKategori().equals("makanan")) {
                System.out.println(menu[i].getNama() + " - Rp" + menu[i].getHarga());
            }
        }

        System.out.println("\n--- MENU MINUMAN ---");
        for (int i = 0; i < jumlahMenu; i++) {
            if (menu[i].getKategori().equals("minuman")) {
                System.out.println(menu[i].getNama() + " - Rp" + menu[i].getHarga());
            }
        }
    }

    static Menu cariMenu(String nama) {
        for (int i = 0; i < jumlahMenu; i++) {
            if (menu[i].getNama().equalsIgnoreCase(nama)) {
                return menu[i];
            }
        }
        return null;
    }

    static void cetakStruk() {
        int total = 0;
        int totalMinuman = 0;

        System.out.println("\n===== STRUK PEMBAYARAN =====");

        for (int i = 0; i < jumlahPesanan; i++) {
            Menu m = cariMenu(pesananNama[i]);
            int subtotal = m.getHarga() * pesananJumlah[i];

            System.out.println(m.getNama() + " x" + pesananJumlah[i] + " = Rp" + subtotal);

            total += subtotal;

            if (m.getKategori().equals("minuman")) {
                totalMinuman += pesananJumlah[i];
            }
        }

        int potonganMinuman = 0;
        if (total > 50000 && totalMinuman >= 2) {
            potonganMinuman = menuHargaMinumanTermurah();
            System.out.println("Promo Beli 1 Gratis 1 Minuman: -Rp" + potonganMinuman);
        }

        int diskon = 0;
        if (total > 100000) {
            diskon = total / 10;
            System.out.println("Diskon 10%: -Rp" + diskon);
        }

        total = total - diskon - potonganMinuman;

        int pajak = total / 10;
        int service = 20000;

        System.out.println("Pajak (10%): Rp" + pajak);
        System.out.println("Service: Rp" + service);

        int totalAkhir = total + pajak + service;

        System.out.println("\nTOTAL AKHIR: Rp" + totalAkhir);
        System.out.println("===========================\n");
    }

    static int menuHargaMinumanTermurah() {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < jumlahMenu; i++) {
            if (menu[i].getKategori().equals("minuman")) {
                if (menu[i].getHarga() < min) {
                    min = menu[i].getHarga();
                }
            }
        }
        return min;
    }

    static void menuPemilik() {
        while (true) {
            System.out.println("\n===== MENU PEMILIK =====");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");
            System.out.print("Pilih: ");

            int p = input.nextInt();
            input.nextLine();

            switch (p) {
                case 1 -> tambahMenu();
                case 2 -> ubahHarga();
                case 3 -> hapusMenu();
                case 4 -> { return; }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    static void tambahMenu() {
        System.out.print("Nama menu: ");
        String nama = input.nextLine();
        System.out.print("Harga: ");
        int harga = input.nextInt();
        input.nextLine();
        System.out.print("Kategori (makanan/minuman): ");
        String kategori = input.nextLine();

        menu[jumlahMenu++] = new Menu(nama, harga, kategori);
        System.out.println("Menu berhasil ditambahkan!");
    }

    static void ubahHarga() {
        tampilkanMenuNomor();
        System.out.print("Pilih nomor menu: ");
        int no = input.nextInt();
        input.nextLine();

        if (no < 1 || no > jumlahMenu) {
            System.out.println("Nomor tidak valid!");
            return;
        }

        System.out.print("Masukkan harga baru: ");
        int harga = input.nextInt();
        input.nextLine();

        menu[no - 1].setHarga(harga);
        System.out.println("Harga berhasil diubah!");
    }

    static void hapusMenu() {
        tampilkanMenuNomor();
        System.out.print("Pilih nomor menu untuk dihapus: ");
        int no = input.nextInt();
        input.nextLine();

        if (no < 1 || no > jumlahMenu) {
            System.out.println("Nomor tidak valid!");
            return;
        }

        for (int i = no - 1; i < jumlahMenu - 1; i++) {
            menu[i] = menu[i + 1];
        }
        jumlahMenu--;

        System.out.println("Menu berhasil dihapus!");
    }

    static void tampilkanMenuNomor() {
        System.out.println("\n===== DAFTAR MENU =====");
        for (int i = 0; i < jumlahMenu; i++) {
            System.out.println((i + 1) + ". " + menu[i].getNama() + " (Rp" + menu[i].getHarga() + ", " + menu[i].getKategori() + ")");
        }
    }
}
