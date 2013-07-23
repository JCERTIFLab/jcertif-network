-- phpMyAdmin SQL Dump
-- version 3.4.10.1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Mar 23 Juillet 2013 à 02:51
-- Version du serveur: 5.5.20
-- Version de PHP: 5.3.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `projet_aac`
--

-- --------------------------------------------------------

--
-- Structure de la table `categories`
--

CREATE TABLE IF NOT EXISTS `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `url_img` varchar(300) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `categories`
--

INSERT INTO `categories` (`id`, `title`, `url_img`, `created`, `modified`) VALUES
(1, 'Android', 'app/webroot/img/logo/android_logo.jpg', '2013-03-10 00:00:00', '2013-03-14 16:32:54'),
(2, 'Java', 'app/webroot/img/logo/Java_Logo.jpg', '2013-03-10 00:00:00', '2013-03-10 00:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `user_id` int(11) NOT NULL,
  `news_id` int(11) NOT NULL,
  `photo_id` int(11) NOT NULL,
  `video_id` int(11) NOT NULL,
  `forum_id` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Contenu de la table `comments`
--

INSERT INTO `comments` (`id`, `content`, `user_id`, `news_id`, `photo_id`, `video_id`, `forum_id`, `created`, `modified`) VALUES
(1, 'content', 10, 1, 0, 0, 0, '2013-03-18 00:00:00', '2013-03-18 00:00:00'),
(3, 'forum 1', 10, 0, 0, 0, 1, '2013-03-21 16:54:21', '2013-03-21 16:54:21'),
(4, 'forum 11', 10, 0, 0, 0, 1, '2013-03-21 16:59:07', '2013-03-21 16:59:07'),
(5, 'test f', 10, 0, 0, 0, 1, '2013-03-21 17:03:55', '2013-03-21 17:03:55'),
(9, 'fxcvu kgcc g hhhh vvffg ghhhhv hhhhh yyyyghhhhh ggggghvv gggg ', 10, 1, 0, 0, 0, '2013-03-23 22:52:06', '2013-03-23 22:52:06'),
(10, 'com video', 10, 0, 0, 2, 0, '2013-03-24 21:16:26', '2013-03-24 21:16:26');

-- --------------------------------------------------------

--
-- Structure de la table `events`
--

CREATE TABLE IF NOT EXISTS `events` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(250) NOT NULL,
  `description` text NOT NULL,
  `url` varchar(500) NOT NULL,
  `img_url` varchar(400) NOT NULL,
  `adress` varchar(300) NOT NULL,
  `ville` varchar(30) NOT NULL,
  `contry` varchar(30) NOT NULL,
  `longitude` varchar(20) NOT NULL,
  `latitude` varchar(20) NOT NULL,
  `date_start` datetime NOT NULL,
  `date_finish` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `events`
--

INSERT INTO `events` (`id`, `title`, `description`, `url`, `img_url`, `adress`, `ville`, `contry`, `longitude`, `latitude`, `date_start`, `date_finish`, `user_id`, `created`, `modified`) VALUES
(1, 'Android Afriqua Chanlenge', 'Competition afriquain d''android', '', 'app/webroot/img/logo/affrica_android_chaleng_logo.jpg', 'Chez soit', 'Ta ville', 'Ton pays', '000000', '00000', '2013-03-03 00:00:00', '2013-03-30 00:00:00', 10, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(2, 'Android Afriqua Chanlenge', 'JCertif Lab', '', 'app/webroot/img/logo/JCertif_Lab_logo.png', 'Chez soit', 'Ta ville', 'Ton pays', '000000', '00000', '2013-03-03 00:00:00', '2013-03-30 00:00:00', 10, '0000-00-00 00:00:00', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `forums`
--

CREATE TABLE IF NOT EXISTS `forums` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(300) NOT NULL,
  `category_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `user_id` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Contenu de la table `forums`
--

INSERT INTO `forums` (`id`, `title`, `category_id`, `content`, `user_id`, `created`, `modified`) VALUES
(1, 'title Forum 1', 1, '<p> c''est mon premier projet </p>', 10, '2013-03-10 00:00:00', '2013-03-10 00:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `news`
--

CREATE TABLE IF NOT EXISTS `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(400) NOT NULL,
  `title` varchar(200) NOT NULL,
  `content` text NOT NULL,
  `img_url` varchar(200) NOT NULL,
  `user_id` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Contenu de la table `news`
--

INSERT INTO `news` (`id`, `url`, `title`, `content`, `img_url`, `user_id`, `created`, `modified`) VALUES
(1, 'http://jcertiflab.github.com/blog/2013/02/25/lancement-projet-m-domotique/', 'Lancement du projet M-Domotique', 'JCertif Lab a le plaisir de vous annoncer le lancement du projet M-Domotique.\r\n\r\nLe projet « M-Domotique » consiste à concevoir et implémenter une application qui apporte une autre solution plus efficace, plus simple à utiliser et moins coûteuse que celles commercialisées actuellement. Elle offre la possibilité à son utilisateur d’administrer et de gérer n’importe quelle local. Ce contrôle peut être effectué soit à distance (à travers un terminal mobile) ou localement. En résumé, le projet offre à l’utilisateur un bouquet de fonctions très utiles tel que :\r\n* Surveiller son établissement à distance quel que soit son emplacement tant qu’il dispose d’une connexion internet.\r\n* Permet de réaliser des différentes actions à distance, lancées depuis le terminal mobile du client ou à partir du serveur, tel que contrôler une machine, allumer ou éteindre une lampe,…\r\n* Consulter les caméras de surveillances à distance, c’est dire recevoir sur le terminal mobile du client une vue instantané prise par la camera.', 'app\\webroot\\img\\img\\firas.jpg', 10, '2013-03-08 23:14:26', '2013-03-08 23:14:26');

-- --------------------------------------------------------

--
-- Structure de la table `photos`
--

CREATE TABLE IF NOT EXISTS `photos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(400) NOT NULL,
  `url` varchar(300) NOT NULL,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(40) CHARACTER SET latin1 NOT NULL,
  `password` varchar(40) CHARACTER SET latin1 NOT NULL,
  `name` varchar(30) CHARACTER SET latin1 NOT NULL,
  `firstname` varchar(30) CHARACTER SET latin1 NOT NULL,
  `date_naissance` date NOT NULL,
  `email` varchar(30) CHARACTER SET latin1 NOT NULL,
  `tel` varchar(30) CHARACTER SET latin1 NOT NULL,
  `payes` varchar(30) CHARACTER SET latin1 NOT NULL,
  `ville` varchar(30) CHARACTER SET latin1 NOT NULL,
  `status` tinyint(1) NOT NULL,
  `admin` tinyint(1) NOT NULL,
  `photo_url` varchar(150) CHARACTER SET latin1 NOT NULL,
  `longitude` varchar(20) CHARACTER SET latin1 NOT NULL,
  `latitude` varchar(20) CHARACTER SET latin1 NOT NULL,
  `description` text CHARACTER SET latin1 NOT NULL,
  `gcm_regid` text CHARACTER SET latin1 NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=13 ;

--
-- Contenu de la table `users`
--

INSERT INTO `users` (`id`, `login`, `password`, `name`, `firstname`, `date_naissance`, `email`, `tel`, `payes`, `ville`, `status`, `admin`, `photo_url`, `longitude`, `latitude`, `description`, `gcm_regid`, `created`, `modified`) VALUES
(10, 'root', 'root', 'éé', 'Firas', '1986-11-22', 'firas.gabsi@gmail.com', '+216 20 738 844', 'Tunis', 'Sousse', 1, 0, 'app\\webroot\\img\\img\\firas.jpg', '10.6161904', '35.8432857', 'Mr Firas GABSI détient un diplôme d’ingénieur en Téléinformatique obtenu à l’Institut Supérieur d’Informatique et des Techniques de Communication de Hammam-Sousse, (Tunisie), en juin2012 – major de sa promotion. Il détient également une licence en réseau informatique obtenu  dans le même établissement en juin 2009 – major de sa promotion et un baccalauréat en science expérimentale obtenu au  Lycée de Hammam-Sousse(Tunisie). Il a réalisé en parallèle avec ses études divers programmes sous Java, à domicile.\r\nDepuis un an, il travaille pour une société de développement au poste de programmeur. \r\nDepuis janvier 2012, il enseigne la programmation Java EE et ULM dans le même institut de sa formation.\r\nMr GABSI a développé une expertise particulière dans l’analyse fonctionnelle, la conception et la réalisation de systèmes informatiques réalisé sous Java EE et Android.\r\nLes principales motivations de Mr GABSI sont la satisfaction de ses clients et le perfectionnement continu dans sa discipline.', 'APA91bGn674q1bNUYdqt07NTFeiKYzXXoNxAMS56bpCGPRn_hg34h2kHk6uBq0B4YMX_Je07W4P8FIDEtq5xHKxhrdolOQdx7Kh8s74mnQ-m8Sm0bzs3PbWSyTfsKkw3TsZAP23Q_-5lGQATNCkUDgoL8--fOOphqQ', '2013-02-28 06:29:11', '2013-03-15 16:11:39'),
(12, 'login', 'login', 'name', 'firstname', '0000-00-00', 'mail', '123456', 'TN', 'ville', 0, 0, '', '10.6162218', '35.8431964', '<p>presentation</p>', 'APA91bGn674q1bNUYdqt07NTFeiKYzXXoNxAMS56bpCGPRn_hg34h2kHk6uBq0B4YMX_Je07W4P8FIDEtq5xHKxhrdolOQdx7Kh8s74mnQ-m8Sm0bzs3PbWSyTfsKkw3TsZAP23Q_-5lGQATNCkUDgoL8--fOOphqQ', '2013-03-22 11:43:39', '2013-03-22 11:43:39');

-- --------------------------------------------------------

--
-- Structure de la table `videos`
--

CREATE TABLE IF NOT EXISTS `videos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(300) NOT NULL,
  `url` varchar(350) NOT NULL,
  `img_url` varchar(350) NOT NULL,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
