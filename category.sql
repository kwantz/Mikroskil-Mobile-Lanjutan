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
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `icon` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`, `icon`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 'Pakaian Wanita', 'ic_dress', '2018-05-25 00:00:00', '2018-05-25 00:00:00', NULL),
(2, 'Pakaian Pria', 'ic_shirt', '2018-05-25 00:00:00', '2018-05-25 00:00:00', NULL),
(3, 'Pakaian Anak', 'ic_child', '2018-05-25 00:00:00', '2018-05-25 00:00:00', NULL),
(4, 'Barang Elektronik', 'ic_television', '2018-05-01 00:00:00', '2018-05-25 00:00:00', NULL),
(5, 'Handphone', 'ic_smartphone', '2018-05-01 00:00:00', '2018-05-25 00:00:00', NULL),
(6, 'Perabot', 'ic_livingroom', '2018-05-01 00:00:00', '2018-05-25 00:00:00', NULL),
(7, 'Makanan', 'ic_dish', '2018-05-01 00:00:00', '2018-05-25 00:00:00', NULL),
(8, 'Alat Tulis', 'ic_pencilcase', '2018-05-01 00:00:00', '2018-05-25 00:00:00', NULL),
(9, 'Olahraga', 'ic_barbell', '2018-05-01 00:00:00', '2018-05-25 00:00:00', NULL),
(10, 'Komputer', 'ic_laptop', '2018-05-01 00:00:00', '2018-05-25 00:00:00', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
