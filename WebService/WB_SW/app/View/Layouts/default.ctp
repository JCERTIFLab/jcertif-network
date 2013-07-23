<html lang="en">
<head>
<title>Backup_All</title>
<meta charset="utf-8">
<?php
echo $this->Html->css('reset')."\n";
echo $this->Html->css('layout')."\n";
echo $this->Html->css('style')."\n";
echo $this->Html->script('jquery-1.6')."\n";
echo $this->Html->script('cufon-yui')."\n";
echo $this->Html->script('cufon-replace')."\n";
echo $this->Html->script('Forum_400.font')."\n";
echo $this->Html->script('jquery.easing.1.3')."\n";
echo $this->Html->script('tms-0.3')."\n";
echo $this->Html->script('tms_presets')."\n";
echo $this->Html->script('atooltip.jquery');
?>

<!--[if lt IE 9]>
	<script type="text/javascript" src="js/html5.js"></script>
	<style type="text/css">
		.slider_bg {behavior:url(js/PIE.htc)}
	</style>
<![endif]-->
<!--[if lt IE 7]>
	<div style='clear:both;text-align:center;position:relative'>
		<a href="http://www.microsoft.com/windows/internet-explorer/default.aspx?ocid=ie6_countdown_bannercode"><img src="http://storage.ie6countdown.com/assets/100/images/banners/warning_bar_0000_us.jpg" border="0" alt="" /></a>
	</div>
<![endif]-->
</head>
<body id="page1">
<div class="body6">
	<div class="body1">
		<div class="body5">
			<div class="main">
<!-- header -->
				<header>
					<h1><a href="index.html" id="logo"></a></h1>
					<nav>
						<ul id="top_nav">
							<li><a href="index.html"><?php echo $this->Html->image('/images/icon_1.gif'); ?></a></li>
							<li><a href="#"><?php echo $this->Html->image('/images/icon_2.gif'); ?></a></li>
							<li class="end"><a href="Contacts.html"><?php echo $this->Html->image('/images/icon_3.gif'); ?></a></li>
						</ul>
					</nav>
					<nav>
						<ul id="menu">
							<li class="active"><a href="http://localhost/backupPfe">Home</a></li>
							<li><a href="http://localhost/WB_SW/Contacts">Contacts</a></li>
							<li><a href="http://localhost/backupPfe/Events">Photos</a></li>
							<li><a href="http://localhost/WB_SW/Messages/">Messageries</a></li>
							<li><a href="http://localhost/WB_SW/Phones/localisation/2">Localisation</a></li>
							
						</ul>
					</nav>
				</header><div class="ic">More Website Templates  @ TemplateMonster.com - August 1st 2011!</div>
<!-- / header -->
<!-- content -->
				<article id="content">
					<div class="slider_bg">
						<div class="slider">
							<ul class="items">
								<li>
								<?php echo $this->Html->image('/images/img1.jpg'); ?>
									
									<div class="banner">
										<strong>Contacts</strong>		
										<p>
										<span>  Sauvegarde les contacts de votre téléphone <br>
											    et envoyer le fichier de contact à l'utilisateur pour une restauration future de vos contacts sur votre téléphone.</span>
											
									     </p>
									</div>
								</li>
								<li>
								<?php echo $this->Html->image('/images/sms.jpg'); ?>
									
									<div class="banner">
										<strong>Messageries</strong>		
										<p>
										<span>  Sauvegarde les messages de votre téléphone <br>
											    et envoyer le fichier de contact à l'utilisateur pour une restauration future de vos messages sur votre téléphone.</span>
											
									     </p>
									</div>
								</li>
								<li>
									<?php echo $this->Html->image('/images/img2.jpg'); ?>
									<div class="banner">
										<strong>Photos<span></span></strong>
										<b></b>
										<p>
											<span>  Sauvegarde les photos de votre téléphone et envoyer le fichier  
											 des photos  à l'utilisateur pour une restauration future de  vos photos sur votre téléphone.</span>
											
										</p>
									</div>
								</li>
								<li>
									<?php echo $this->Html->image('/images/img2.jpg'); ?>
									<div class="banner">
										<strong>Photos<span></span></strong>
										<b></b>
										<p>
											<span>  Sauvegarde les photos de votre téléphone et envoyer le fichier  
											 des photos  à l'utilisateur pour une restauration future de  vos photos sur votre téléphone.</span>
											
										</p>
									</div>
								</li>
								<li>
									<?php echo $this->Html->image('/images/img3.jpg'); ?>
									<div class="banner">
										<strong>Localisation </strong>									
										<p>
											<span> Le service de localisation est le moyen idéal<br>
											 dans le cas de perte, on peut localiser notre Smartphone, 
											 lui envoyer un son ou message, 
											 verrouiller le Smartphone et effacer les données.</span>
										</p>
									</div>
								</li>
							</ul>
						</div>
					</div>
					<div class="wrap">
						
					</div>
				</article>
			</div>
		</div>
	</div>
</div>
<div class="body2">
	<div class="main">
		<article id="content2">
			<div class="wrapper">
				<section class="col1 pad_left1">
					<h2></h2>
					<?php echo $content_for_layout ;?>
					<script type="text/javascript">
$(window).load(function(){
	$('.slider')._TMS({
		show:0,
		preset:'diagonalExpand',
		easing:'easeOutQuad',
		duration:800,
		pagination:true,
		slideshow:3000,
		banners:true,
		waitBannerAnimation:false,
		bannerShow:function(banner){
			banner
				.css({opacity:'0'})
				.stop()
				.animate({opacity:'1'},700, function(){$(this).css({opacity:'none'})})
		},
		bannerHide:function(banner){
			banner
				.stop()
				.animate({opacity:'0'},700)
		}
	})
});
</script>	
				</section>
			<!-- 	<section class="col2 pad_left1">
					<h2>Testimonials</h2>
					<ul class="testimonials">
						<li>
							<span>1</span>
							<p>
								“Remperam eaquepsa quae abillo inventore vertatis.”
								<img src="images/signature1.jpg" alt="">
							</p>
						</li>
						<li>
							<span>2</span>
							<p>
								“Quasi arctecto beatae vitae dicta sunt explicabo.”
								<img src="images/signature2.jpg" alt="">
							</p>
						</li>
						<li>
							<span>3</span>
							<p>
								“Nemo enim ipsam volupta<br>
								tem quia voluptas.”<img src="images/signature3.jpg" alt="">
							</p>
						</li>
					</ul>
				</section> -->
			</div>
		</article>
<!-- / content -->
	</div>
</div>
<div class="body3">
	<div class="body4">
		<div class="main">
<!-- footer -->
			<footer>
				<div class="wrapper">
					<section class="col1 pad_left1">
						<h3>Amira Haoues: <span>haouesamira1@gmail.com</span></h3>
						<a rel="nofollow" href="http://www.dotit.com.tn" target="_blank"> Website of societe</a> www.dotit.com.tn
					</section>
					<section class="col2 pad_left1">
						<h3>Follow Us </h3>
						<ul id="icons">
						<li><?php echo $this->Html->image('/images/icon1.gif'); ?> </li>
						<li><?php echo $this->Html->image('/images/icon2.gif'); ?> </li>
						<li><?php echo $this->Html->image('/images/icon3.gif'); ?> </li>
						<li><?php echo $this->Html->image('/images/icon4.gif'); ?> </li>
						<li><?php echo $this->Html->image('/images/icon5.gif'); ?> </li>
						
						</ul>
					</section>
				</div>
				<!-- {%FOOTER_LINK} -->
			</footer>
<!-- / footer -->
		</div>
	</div>
</div>
<script type="text/javascript"> Cufon.now(); </script>

</body>
</html>