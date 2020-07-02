	var $filterableRows = $('#leavetable').find('tr').not(':first'),
	$inputs = $('.search-key');

	$inputs.on('input', function() {

	$filterableRows.hide().filter(function() {
	return $(this).find('td').filter(function() {

	var tdText = $(this).text().toLowerCase(),
			inputValue = $('#' + $(this).data('input')).val().toLowerCase();

	return tdText.indexOf(inputValue) != -1;

	}).length == $(this).find('td').length;
	}).show();

	});