 <script>
$(function()
{
    var $dialog = $("#view_dialog").dialog(
        {
            autoOpen: false,
            title: 'View Local Clock',
            height: 200,
            width: 1200,
            resizable: true,
            modal: true,
            buttons:
            {
                "Ok": function()
                {
                    $(this).dialog("close");
                }
            }
        });
    $(".view_dialog").click(function()
    {
        $dialog.load($(this).attr('href'), function ()
                {
                    $dialog.dialog('open');
                });
        return false;
    });
});
</script>
<?php $fermer="fermer";
$delete="delete";
$sound="sound";
 ?>
<div class="actions">
	<h3><?php __('Actions'); ?></h3>
	<ul>
	<?php foreach ($data as $e){$e=$e['Phone'];?>
<li><?php echo $this->Html->link(__('Contact', true), array('controller' => 'contacts', 'action' => 'affichecontact/'.$e['user_id'])); ?> </li>
<li><?php echo $this->Html->link(__('Message', true), array('controller' => 'messages', 'action' => 'affichemessage/'.$e['user_id'])); ?> </li>
<li><?php echo $this->Html->link(__('Email', true), array('controller' => 'emails', 'action' => 'admin_afficheemail/'.$e['user_id'])); ?> </li>
<li><?php echo $this->Html->link(__('Event', true), array('controller' => 'events', 'action' => 'afficheevent/'.$e['user_id'])); ?> </li>
<li><?php echo $this->Html->link(__('Phone', true), array('controller' => 'phones', 'action' => 'affichephone/'.$e['user_id'])); ?> </li>
	<?php }?>	
	</ul>
</div>
<div class="users view">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.js"></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript">
$(function(){
		<?php foreach ($data as $e){$e=$e['Phone'];?>
         var latlng = new google.maps.LatLng(<?php echo $e['lat'];?>,<?php echo $e['lng'];?>);
         var map = new google.maps.Map(document.getElementById('gmap'),{ zoom:10,
		                                                                center:latlng,
																		mapTypeId:google.maps.MapTypeId.ROADMAP});
		
		var marker= new google.maps.Marker({position:latlng,
											map: map,
											draggable:false});
		marker.idEvent= <?php echo $e['id'];?>;
		<?php }?>
});
</script>
<div id="gmap" style="width:400px;height:300px;"></div>
<?php foreach ($data as $e):$e=$e['Phone'];?>
<div class="event event<?php echo $e['id'];?>">
<h1><?php echo $e['type']; ?></h1>
<script>
function myFunction()
{
alert("I am an alert box!");
}
 </script>
 <?php echo $this->Html->link(__('Recherche_Phone/Song', true), array('controller' => 'phones', 'action' => 'fermerphone/'.$e['id'].'/'.$sound));?>
<?php echo $this->Html->link(__('Fermer', true), array('controller' => 'phones', 'action' => 'fermerphone/'.$e['id'].'/'.$fermer));?>
 <?php echo $this->Html->link(__('Contact_DeleteAll', true), array('controller' => 'phones', 'action' => 'fermerphone/'.$e['id'].'/'.$delete));?>


<?php endforeach;?>

</div>
<div id="view_dialog"></div>



		