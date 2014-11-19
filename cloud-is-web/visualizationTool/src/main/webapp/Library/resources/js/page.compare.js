/*
 * Adds a new object to comparisson ribbon.
 */
var addComparissonObject = function() {
	console.log("clicked");
	
	// Get template object
	var comparrisonRibbon = $('.objectSelectorWrapper');
	var wellHolder = comparrisonRibbon.find('.well > .wellContentHolder');
	var wellItem = comparrisonRibbon.find('.well > .wellItemTemplate').clone();				
	// remove unwanted classes
	wellItem.removeClass('noDisplay');
	wellItem.removeClass('wellItemTemplate');				
	// Fill item properties
		
	
	// Append item to well
	wellHolder.append(wellItem);
	
	// Calculate new widths
	var itemCount = 1;
	var ribboWidth = parseInt(comparrisonRibbon.find('.inner').css('width'), 10);
	var addControlWitdh = parseInt($('.objectSelectorWrapper .addNewControl').css('width'), 10);
	var itemsSpace = ribboWidth - addControlWitdh;
	var itemMinWidth = parseInt(wellHolder.find('.wellItem').css('min-width'), 10);
	if ((itemCount * itemMinWidth) > itemsSpace)
	{
		var itemWitdh = itemsSpace / itemCount;
		wellHolder.find('.wellItem').css('width', itemWitdh);
	}
};

$(document).ready(function(){
	// Init tabber controls
	var myTabber = Object.create(ui.tabber);
	myTabber.init("innerNavMenu", false);
	
	// Check if comparison is requested (via the url)
	// add get the appropriate data
	
	// TODO
	
	
	
	// Init events for the comparison ribbon
	$('.objectSelectorWrapper .addNewControl').off('click');
	$('.objectSelectorWrapper .addNewControl').on('click', addComparissonObject);
});