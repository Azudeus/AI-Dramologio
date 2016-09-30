/*
	Simple drag and drop
*/
$(document).ready(function(){
		$('.event').on("dragstart", function (event) {
			  var dt = event.originalEvent.dataTransfer;
			  dt.setData('Text', $(this).attr('id'));
			});
	    $('table td').on("dragenter dragover drop", function (event) {	
		   event.preventDefault();
		   if (event.type === 'drop') {
			  var data = event.originalEvent.dataTransfer.getData('Text',$(this).attr('id'));
			  if($(this).find('span').length===0){
				   de=$('#'+data).detach();
				   de.appendTo($(this));	
				  }
			 
		   };
	   });
})