/**
 * ToDoList関連画面
 */

 $(function(){
	 $('#todolist tbody tr').on('click', function(){
		 console.log(this);
		$('#todolist tbody tr').removeClass('table-chosen');
		$(this).addClass('table-chosen');
	
		$('#updateTodolist').removeAttr('disabled');
	 });
 })