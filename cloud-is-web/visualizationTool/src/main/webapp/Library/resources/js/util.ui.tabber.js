$(document).ready(function(){
//	var context = ".tabber";
//	
//	var tabberNav = $(context + ' [data-tabber-nav]');
//	var tabberPages = $(context + ' [data-tabber-pages]');
//	
//	tabberNav.on('click', '[data-tabber-navTile]', function(){
//		var tile = $(this);
//		if(!tile.hasClass('disabled'))
//		{
//			tabberNav.find('[data-tabber-navTile]').each(function(){
//				var t = $(this);
//				t.removeClass('selected');
//			});
//			tile.addClass('selected');
//			
//			var targetId = tile.data('tabber-ref');		
//			tabberPages.find('[data-tabber-page]').each(function(){
//				var page = $(this);
//				page.addClass('noDisplay');
//				if(page.data('tabber-pageid') == targetId)
//					page.removeClass('noDisplay');
//			});
//		}
//	});	
});

ui = {};
ui.tabber = {
		prototypes : new Object(),
				
		init : function(id, editable) {			
			console.log("init tabber : " + id);
			
			this.controlId = id;
			if (this.prototypes[this.controlId] != null)
				return true;
			
			// Init to prevent future executions
			this.prototypes[this.controlId] = Object.create(ui.tabber.obj);	
			
			
			if(editable == null || editable == false)
				this.prototypes[this.controlId].editable = false;
			else
				this.prototypes[this.controlId].editable = true;
			
			var tabber = $('[data-tabber-id="'+ id +'"]');
			this.prototypes[this.controlId].tabber = tabber;
			this.prototypes[this.controlId].tabberNav = tabber.find('[data-tabber-nav][data-tabber-parentId="'+ this.controlId +'"]');
			this.prototypes[this.controlId].tabberPages = tabber.find('[data-tabber-pages][data-tabber-parentId="'+ this.controlId +'"]');
			
			// Assign events
			
			// Nav Tile onClick
			this.prototypes[this.controlId].tabberNav.on('click', '[data-tabber-navTile] > span', function(ev){
				// Stop bubbling
				ev.stopPropagation();
				
				var tile = $(this).closest('[data-tabber-navTile]');
				var tabberId = $(this).closest('[data-tabber-nav]').data('tabber-parentid');
				
				var tabberObj = ui.tabber.prototypes[tabberId];
				
				if(!tile.hasClass('disabled'))
				{
					tabberObj.tabberNav.find('[data-tabber-navTile]').each(function(){
						var t = $(this);
						t.removeClass('selected');
					});
					tile.addClass('selected');
					
					var targetId = tile.data('tabber-ref');
					// Redundant check, a miss configuration may lead to error.
					if(targetId != null && typeof targetId != 'undefined') {
						tabberObj.tabberPages.find('[data-tabber-page]').each(function(){
							var page = $(this);
							page.addClass('noDisplay');
							if(page.data('tabber-pageid') == targetId)
								page.removeClass('noDisplay');
						});
						$(document).trigger('ui.tabber.change', {'tabberId':tabberId, 'targetId':targetId});
					}
				}				
				
			});	
			
			// Nav Tile onClose Click
			if(this.prototypes[this.controlId].editable)
			{
				this.prototypes[this.controlId].tabberNav.find('[data-tabber-navTile] button.close').not('[data-tabber-sticked]').on('click', function(ev){
					
					// Stop bubbling
					ev.stopPropagation();
					
					var tile = $(this);
					var tabberId = $(this).closest('[data-tabber-nav]').data('tabber-parentid');				
					var tabberObj = ui.tabber.prototypes[tabberId];
					
					console.log("close");				
				});	
			}
			
		}
};
ui.tabber.obj = {
		tabber : null,
		tabberNav : null,
		tabberPages : null,
		editable: null
};