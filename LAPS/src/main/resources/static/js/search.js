$(document).ready(function(){
	  $("#userInput").on("keyup", function() {
	    var value = $(this).val().toLowerCase();
	    $("#leaveRecordTable tr").filter(function() {
	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	});