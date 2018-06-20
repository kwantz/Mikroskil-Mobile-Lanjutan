-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 20, 2018 at 10:12 AM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `picture` varchar(255) NOT NULL,
  `seen` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `discount` int(11) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`id`, `category_id`, `name`, `description`, `picture`, `seen`, `stock`, `price`, `discount`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 'Asus Zenfone Go ZB452KG', 'Asus Zenfone Go ZB452KG hadir dengan prosesor Quad-core 1.2 GHz Cortex-A7 yang powerful dan RAM 1 GB untuk menghandel segala kebutuhan aplikasi android, memberikan pengalaman bermain game yang tak terlupakan. Layar berukuran 4.5 inchi dengan resolusi 480 x 854 pixels siap menampilkan visual gambar dengan tampilan yang cerah dan warna yang jernih dan tajam.Memiliki dual kamera 5 MP pada bagian kamera depan dan 2 MP pada bagian belakang yang akan mengabadikan moment Anda bersama dengan teman-teman dan keluarga. Di lengkapi dengan dual SIM slot dengan ukuran Micro SIM dan Asus ZB452KG juga di dukung dengan memori eksternal hingga kapasitas 64 GB. Hadir dengan desain elegan dan ergonomis dengan harga terjangkau, Asus Zenfone Go sangat ideal untuk menjadi smartphone pilihan Anda.\r\n\r\nZenFone Go memiliki PixelMaster, teknologi eksklusif ASUS yang selalu menangkap gambar terbaik dalam setiap skenario. Para ahli berhasil menciptakan sebuah alat sederhana yakni ZenFone Go yang dapat mengubah Anda menjadi ahli dalam fotografi - dan PixelMaster akan selalu menjadi asisten Anda dimanapun!', 'https://ecs7.tokopedia.net/img/cache/300/catalog/2017/7/31/15524491/15524491_c30ac33e-c550-4912-9fc9-d6a53f542e2d.png.webp', 10, 100, 136500, NULL, '2018-05-24 00:00:00', '2018-05-25 00:00:00', NULL),
(2, 2, 'Lenovo IP 110', 'Lenovo IP 110 merupakan sebuah rilisan lenovo berjenis notebook dengan harga yang sangat terjangkau dan spesifikasi yang terjamin untuk di gunakan aktivitas sehari harinya.Lenovo IP 110 di lengkapi dengan layar 14 inch beresolusi 1366 x 768 pixels dan disertai dengan processor Intel Celeron N3160 (1.60 GHz, 2M Cache) dan tersedia dengan dua pilihan core I5 atau I7', 'https://ecs7.tokopedia.net/img/cache/300/catalog/2017/10/24/14726723/14726723_486e71a2-ece5-4d2d-9c04-e3a2c3d99d21.jpeg.webp', 1, 5, 7050000, NULL, '2018-05-25 00:00:00', '2018-05-25 00:00:00', NULL),
(3, 3, 'Sennheiser MX170', 'Sennheiser MX 170, earphone portable dengan harga cukup terjangkau yang dapat menghasilkan kualitas suara yang kencang dan penuh detil. Kualitas bass yang dihasilkan juga kuat dan menendang. Desain yang simetris memungkinkan kemudahan pemakaian dan penyimpanan.\r\n\r\nKualitas suara yang dihasilkan oleh in-the-ear headphone Sennheiser MX170 kencang dan penuh detil. Earphone ini dapat menghasilkan suara stereo serta bass yang menendang saat mendengarkan music. Dengan frekuensi response 22 20,000 Hz, anda akan mendengar suara yang penuh detil. Selain itu, earphone ini juga cocok dipakai outdoor karena dapat meredam atau mengurangi suara bising dari lingkungan sekitar.', 'https://ecs7.tokopedia.net/img/cache/300/catalog/2017/10/23/17027990/17027990_891bed59-f07f-4f54-b24b-6557505240e7.jpeg.webp', 1, 100, 285000, NULL, '2018-05-01 00:00:00', '2018-05-25 00:00:00', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
