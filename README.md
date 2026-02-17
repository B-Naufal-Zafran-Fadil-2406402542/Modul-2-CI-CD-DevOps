## Reflection

### 1. Clean Code Principles Applied
Selama pengembangan fitur *Edit* dan *Delete*, saya telah menerapkan beberapa prinsip Clean Code sesuai dengan standar yang dipelajari:

* **Meaningful Names**: Saya memastikan nama metode mengungkapkan tujuannya secara jelas. Misalnya, menggunakan `update` dan `delete` dalam `ProductService` alih-alih nama yang redundan seperti `updateProductData`, karena konteksnya sudah jelas di dalam kelas produk.
* **Small Functions**: Fungsi-fungsi yang saya buat tetap ringkas dan fokus pada satu tanggung jawab (**Single Responsibility Principle**).
* **Error Handling with Exceptions**: Saya menghindari penggunaan *return codes* atau pengembalian *null*. Sebaliknya, saya menggunakan *Custom Exception* (`ProductNotFoundException`) untuk menangani kasus objek yang tidak ditemukan. Hal ini memisahkan algoritma utama dengan logika penanganan kesalahan.
* **Don't Repeat Yourself (DRY)**: Saya menyederhanakan alur kontrol di *Controller* agar tidak ada pengulangan pernyataan `return` yang identik di dalam blok `try` dan `catch`.



### 2. Secure Coding Practices Applied
Keamanan aplikasi ditingkatkan dengan memperhatikan cara data dimanipulasi:

* **Avoiding GET for State Changes**: Saya tidak menggunakan `@GetMapping` untuk operasi penghapusan. Sebagai gantinya, saya menggunakan `@PostMapping` untuk memastikan bahwa perubahan status server (penghapusan data) memerlukan aksi eksplisit dari form, guna mencegah penghapusan tidak sengaja oleh *web crawler*.
* **Data Binding & Hidden Fields**: Saya menggunakan `<input type="hidden">` di Thymeleaf untuk membawa `productId` secara aman dalam objek `Product` saat melakukan *POSTing* data edit, memastikan ID yang benar diperbarui di sisi server.
* **Robust Exception Handling**: Dengan menangkap pengecualian di level *Controller*, saya mencegah aplikasi menampilkan *Whitelabel Error Page* yang teknis kepada pengguna, sehingga menjaga pengalaman pengguna tetap mulus.



### 3. Mistakes & Improvements
Berdasarkan evaluasi mandiri terhadap kode saat ini, berikut adalah beberapa hal yang dapat diperbaiki:

* **Silent Failures**: Saat ini, jika produk tidak ditemukan saat proses hapus, aplikasi hanya melakukan *redirect* tanpa memberi notifikasi.
    * **Perbaikan**: Menggunakan `RedirectAttributes` untuk mengirimkan pesan sukses atau error ke halaman daftar produk agar pengguna mendapatkan umpan balik yang jelas.
* **Validation**: Belum ada validasi input yang kuat untuk memastikan nama produk tidak kosong atau kuantitas bernilai positif.
    * **Perbaikan**: Menerapkan Bean Validation (`@NotBlank`, `@Min`) pada model `Product` dan menggunakan `@Valid` di Controller.

## Reflection 2

### Unit Testing & Code Coverage

Setelah mengimplementasikan unit test untuk model dan repository, saya merasa jauh lebih aman dan percaya diri dalam melakukan perubahan kode karena setiap fitur dasar telah memiliki jaring pengaman otomatis. Menurut saya, jumlah unit test yang dibuat dalam satu kelas haruslah cukup untuk mencakup seluruh alur logika, baik itu skenario sukses maupun skenario gagal (negative cases). Untuk memastikan bahwa pengujian kita sudah memadai, kita dapat menggunakan metrik *code coverage* yang membantu mengidentifikasi bagian kode mana yang belum tersentuh oleh pengujian sama sekali. Namun, sangat penting untuk dipahami bahwa mencapai 100% *code coverage* bukan berarti kode kita sepenuhnya bebas dari bug atau kesalahan logika. *Code coverage* hanyalah indikator kuantitatif yang menunjukkan baris mana yang dieksekusi selama tes, tetapi tidak menjamin kualitas asersi atau validasi terhadap berbagai variasi input dan kondisi ekstrem yang mungkin terjadi di dunia nyata. Oleh karena itu, selain mengejar angka *coverage*, kita juga harus fokus pada kualitas skenario pengujian yang relevan.

### Functional Testing & Clean Code

Berdasarkan pengamatan saya terhadap pembuatan *functional test suite* baru (seperti pengujian jumlah item di daftar produk), menulis ulang prosedur *setup* dan variabel instansi yang sama persis akan sangat menurunkan kualitas dan kebersihan kode. Masalah utama yang muncul dari pendekatan ini adalah terjadinya redundansi kode atau pelanggaran prinsip **DRY (Don't Repeat Yourself)**. Duplikasi kode semacam ini membuat pemeliharaan menjadi sulit; apabila di masa depan terdapat perubahan konfigurasi port atau URL dasar, kita harus mengubahnya di setiap file pengujian satu per satu, yang mana sangat rawan akan kesalahan manusia (*human error*). Selain itu, hal ini membuat struktur proyek terlihat berantakan dan tidak profesional.

Untuk meningkatkan kualitas kode agar lebih bersih (*clean code*), saya menyarankan penerapan konsep **inheritance** dalam pengujian. Kita dapat membuat sebuah *Base Class* fungsional (misalnya `FunctionalTest.java`) yang berisi semua konfigurasi umum seperti `@LocalServerPort`, `@Value` untuk base URL, dan metode `@BeforeEach` untuk inisialisasi. Dengan cara ini, kelas pengujian lainnya seperti `HomePageFunctionalTest`, `CreateProductFunctionalTest`, dan kelas baru lainnya hanya perlu melakukan *extends* ke *Base Class* tersebut. Pendekatan ini membuat kode menjadi lebih modular, mudah dibaca, dan jauh lebih mudah untuk dikelola dalam jangka panjang.

---

### Penjelasan Tambahan Terkait Struktur Proyek

Berikut adalah rangkuman perbaikan yang telah dilakukan untuk memenuhi kriteria rubrik:
* **Correctness & Creativity**: Mengimplementasikan `FunctionalTest.java` sebagai *base class* untuk menghindari duplikasi kode dan mempercantik tampilan halaman menggunakan Bootstrap 5 tanpa merusak pengujian fungsional.
* **Robust Testing**: Menambahkan pengujian fungsional untuk fitur *Delete* dengan penanganan `NoSuchElementException` menggunakan XPath yang dinamis dan sinkronisasi `Thread.sleep`.
* **Clean Code**: Menghapus penggunaan `@MockBean` yang sudah *deprecated* pada Spring Boot 3.4.0 dan beralih ke Mockito murni agar kode tetap *up-to-date*.

---
## Reflection 3

### 1. Code Quality Issues and Fixes

During this exercise, I addressed several code quality and configuration issues to improve the project's stability and maintainability.

*   **Low Code Coverage:** The initial code coverage was approximately 48%. I increased it to 100% by implementing a comprehensive suite of unit tests for the `ProductController`, `ProductServiceImpl`, `EshopApplication`, and `ProductRepository`. My strategy involved analyzing the JaCoCo coverage report to identify uncovered classes and methods, then writing targeted tests using `MockMvc` for the controller and Mockito for the service layer to cover all execution paths, including success and failure scenarios.

*   **Build Failures due to PMD Configuration:** The build was failing due to an incorrect PMD configuration in `build.gradle.kts`. The `ignoreFailures` property was inaccessible. I resolved this by using the `tasks.withType<Pmd> { ignoreFailures = false }` configuration, which is the correct way to configure PMD tasks in this Gradle version, ensuring that the build fails on any linter violations.

*   **CI/CD Test Failures (Case-Sensitivity):** The `ProductControllerTest` was failing in the GitHub Actions pipeline because the view names returned by the controller (e.g., `productList`) did not exactly match the template file names (e.g., `ProductList.html`) on a case-sensitive file system. I fixed this by renaming the return values in `ProductController.java` to match the template file names exactly and updating the corresponding tests.

*   **SonarCloud Coverage Reporting Issues:** SonarCloud was incorrectly reporting 0% code coverage. This was due to two issues:
    1.  The JaCoCo XML report, which SonarCloud needs for analysis, was not being generated. I fixed this by explicitly enabling XML report generation in the `jacocoTestReport` task in `build.gradle.kts`.
    2.  The SonarQube configuration was not explicitly pointing to the JaCoCo report. I resolved this by adding the `sonar.coverage.jacoco.xmlReportPaths` property to the `sonar` block in `build.gradle.kts`, ensuring SonarCloud could find and process the coverage data.

*   **SonarQube Security Hotspot (Dependency Verification):** SonarQube identified a security risk because Gradle's dependency verification was not enabled. I addressed this by running `./gradlew --write-verification-metadata pgp,sha256 --export-keys` to generate the `verification-metadata.xml` and keyring files, enabling Gradle to verify the integrity and authenticity of the project's dependencies.

### 2. CI/CD Workflow Analysis

The current CI/CD implementation has successfully met the definition of **Continuous Integration (CI)**, but not **Continuous Deployment (CD)**.

The project has a robust CI process. The GitHub Actions workflows automatically trigger on every `push` and `pull_request` to build the application, run the full test suite (`./gradlew test`), and perform static code analysis with SonarCloud (`./gradlew build sonar`). This ensures that every change is automatically tested and verified, which is the core principle of Continuous Integration.

However, the pipeline does not include Continuous Deployment. After a successful build and analysis, there are no automated steps to deploy the application to a staging or production environment, such as a PaaS like Heroku or AWS Elastic Beanstalk. A complete CD pipeline would automate the release process, allowing new changes that pass all checks to be deployed to users without manual intervention.

---
