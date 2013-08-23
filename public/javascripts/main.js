$(document).ready(function(){
	hide_shelves();

	var shelves = $(".shelve");

	if(shelves.size()>0){
		$(shelves[0]).show();
	}

	$(".tab").on("click",function(){
		display_shelve(this);
	})
});

function display_shelve(selected_tab){
	hide_shelves();
	var id = "#" + $(selected_tab).attr("id") + "-tab";
	show_shelve(id);
}

function hide_shelves(){
	$(".shelve").hide();
}

function show_shelve(id){
	$(id).show(500);
}