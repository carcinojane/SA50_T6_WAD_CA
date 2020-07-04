
   
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
  
  
