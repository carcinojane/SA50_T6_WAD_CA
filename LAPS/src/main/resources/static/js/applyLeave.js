/*   $('#leaveType').on('change',function(){
        if( $(this).val()=='Compensation' ){
        $("#time").show()
        }
        else{
        $("#time").hide()
        }
    });*/
   
/*   $('#leaveType').on('change',function(){
	     var selection = $(this).val();
	    switch(selection){
	    case "1":
	    $("#time").show()
	   break;
	    default:
	    $("#time").hide()
	    }
	});*/

   
  var renderTimeFields = function(){
	  var leaveType = $("#leaveType").find("option:selected").val();

	   if(leaveType == "Compensation"){
		   $('#fromTime').show();
		   $('#toTime').show();
	   } else{
		   $('#fromTime').hide();
		   $('#toTime').hide();
	   }
  }
  ;
  $(function(){
	  renderTimeFields();
	  $("#leaveType").change(function(){
		   renderTimeFields();
	   });
  });
  
  
