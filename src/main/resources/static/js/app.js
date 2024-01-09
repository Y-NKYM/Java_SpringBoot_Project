/**
 * ToDoList関連画面
 */

 $(function(){
	 $('#todolist tbody tr').on('click', function(){
		
	//選択時のCSS表示
		$('#todolist tbody tr').removeClass('table-chosen');
		$(this).addClass('table-chosen');
	
	//編集ボタンのクリックを可能にする
		$('#editTodolistBtn').removeAttr('disabled');
		$('#checkDeleteTodolistBtn').removeAttr('disabled');
		inputSelectedId($(this));
	 });
	 
	 $('#confirmDeleteTodolistBtn').click(function(){
		$('#deleteTodolistBtn').trigger('click');
	 });
 })
 
 function inputSelectedId(row){
	 row.find('td').each(function(){
		 var columnId = $(this).attr('id');
		 if(columnId.startsWith('id')){
			 $('#selectedTodolistId').val($(this).text());
			 return false;
		 }
	 });
 }