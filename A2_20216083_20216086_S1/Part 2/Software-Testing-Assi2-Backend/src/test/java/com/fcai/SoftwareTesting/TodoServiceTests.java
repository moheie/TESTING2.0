package com.fcai.SoftwareTesting;

import com.fcai.SoftwareTesting.todo.Todo;
import com.fcai.SoftwareTesting.todo.TodoCreateRequest;
import com.fcai.SoftwareTesting.todo.TodoService;
import com.fcai.SoftwareTesting.todo.TodoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class TodoServiceTests {


    @Autowired
    private TodoService todoService = new TodoServiceImpl();


    //testCreateTodoWithValidArgument
    @Test
    void testCreateTodoWithValidArgument() {

        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("title", "description");
         Todo newTodo = todoService.create(todoCreateRequest);
         assertEquals("title", newTodo.getTitle());
         assertEquals("description", newTodo.getDescription());

    }

    //testCreateTodoWithEmptyTitle
    @Test
    void testCreateTodoWithEmptyTitle() {

        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("", "description"); // empty title

        assertThrows(IllegalArgumentException.class,
                () -> todoService.create(todoCreateRequest),
                "Todo title cannot be empty");

    }

    //testCreateTodoWithEmptyDescription
    @Test
    void testCreateTodoWithEmptyDescription() {

        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("title", ""); // empty description

        assertThrows(IllegalArgumentException.class,
                () -> todoService.create(todoCreateRequest),
                "Todo description cannot be empty");

    }

    //testCreateTodoWithNullTodo
    @Test
    void testCreateTodoWithNullTodo() {

        assertThrows(IllegalArgumentException.class,
                () -> todoService.create(null), // null todo
                "Todo cannot be null");

    }

    //testReadTodoWithValidId
    @Test
    void testReadTodoWithValidId() {

        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("title", "description");
        Todo newTodo = todoService.create(todoCreateRequest);
        Todo todo = todoService.read(newTodo.getId());

        assertEquals(newTodo.getId(), todo.getId());
        assertEquals(newTodo.getTitle(), todo.getTitle());
        assertEquals(newTodo.getDescription(), todo.getDescription());
        assertFalse(todo.isCompleted());

    }

    //testReadTodoWithInvalidId
    @Test
    void testReadTodoWithEmptyId() {

        assertThrows(IllegalArgumentException.class,
                () -> todoService.read(""), // empty id
                "Todo id cannot be empty");

    }

    //testReadTodoWithNullId
    @Test
    void testReadTodoWithNullId() {

        assertThrows(IllegalArgumentException.class,
                () -> todoService.read(null), // null id
                "Todo id cannot be null");

    }
    //testReadTodoWithNotFoundId
    @Test
    void testReadTodoWithNotFoundId() {

        assertThrows(IllegalArgumentException.class,
                () -> todoService.read("1"), // not found id
                "Todo not found");

    }

    //testUpdateTodoWithValidId
    @Test
    void testUpdateTodoWithValidId() {

        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("title", "description");
        Todo newTodo = todoService.create(todoCreateRequest);
        Todo todo = todoService.update(newTodo.getId(), true);

        assertEquals(newTodo.getId(), todo.getId());
        assertEquals(newTodo.getTitle(), todo.getTitle());
        assertEquals(newTodo.getDescription(), todo.getDescription());
        assertEquals(true, todo.isCompleted());

    }

    //testUpdateTodoWithEmptyId
    @Test
    void testUpdateTodoWithEmptyId() {

        assertThrows(IllegalArgumentException.class,
                () -> todoService.update("", true), // empty id
                "Todo id cannot be empty");

    }

    @Test
    void testUpdateTodoWithNullId() {

        assertThrows(IllegalArgumentException.class,
                () -> todoService.update(null, true),
                "Todo id cannot be null");

    }

    @Test
    void testUpdateTodoWithNotFoundId() {

        assertThrows(IllegalArgumentException.class,
                () -> todoService.update("1", true),
                "Todo not found");

    }


    @Test
    void testDeleteTodoWithValidId() {

        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("title", "description");
        Todo newTodo = todoService.create(todoCreateRequest);
        todoService.delete(newTodo.getId());

        assertThrows(IllegalArgumentException.class,
                () -> todoService.read(newTodo.getId()),
                "Todo not found");

    }

    @Test
    void testDeleteTodoWithEmptyId() {

        assertThrows(IllegalArgumentException.class,
                () -> todoService.delete("")); // empty id

    }

    @Test
    void testDeleteTodoWithNullId() {

        assertThrows(IllegalArgumentException.class,
                () -> todoService.delete(null)); // null id
    }

    @Test
    void testDeleteTodoWithNotFoundId() {

        assertThrows(IllegalArgumentException.class,
                () -> todoService.delete("1")); // not found id
    }

    //after creating 2 lists, delete one of them and create a new one but new one gets same id as the remaining one
    @Test
    void testCreateAfterDelete() {

            TodoCreateRequest todoCreateRequest1 = new TodoCreateRequest("title1", "description1");
            TodoCreateRequest todoCreateRequest2 = new TodoCreateRequest("title2", "description2");

            Todo newTodo1 = todoService.create(todoCreateRequest1);
            Todo newTodo2 = todoService.create(todoCreateRequest2);

            //delete the first one
            todoService.delete(newTodo1.getId());

            //add first one again
            Todo newTodo3 = todoService.create(todoCreateRequest1);

            //check if the id is different
            assertTrue(!newTodo3.getId().equals(newTodo2.getId()));

    } // fails they have same id


    @Test
    void testListTodos() {

        TodoCreateRequest todoCreateRequest1 = new TodoCreateRequest("title1", "description1");
        TodoCreateRequest todoCreateRequest2 = new TodoCreateRequest("title2", "description2");
        TodoCreateRequest todoCreateRequest3 = new TodoCreateRequest("title3", "description3");

        Todo newTodo1 = todoService.create(todoCreateRequest1);
        Todo newTodo2 = todoService.create(todoCreateRequest2);
        Todo newTodo3 = todoService.create(todoCreateRequest3);

        List<Todo> list = new ArrayList<>();
        list.add(newTodo1);
        list.add(newTodo2);
        list.add(newTodo3);

        assertEquals(list, todoService.list());

    }

    @Test
    void testListTodosWithEmptyList() {

        List<Todo> list = new ArrayList<>();

        assertEquals(list, todoService.list());

    }



    @Test
    void testListCompletedTodos() {

        TodoCreateRequest todoCreateRequest1 = new TodoCreateRequest("title1", "description1");
        TodoCreateRequest todoCreateRequest2 = new TodoCreateRequest("title2", "description2");
        TodoCreateRequest todoCreateRequest3 = new TodoCreateRequest("title3", "description3");

        Todo newTodo1 = todoService.create(todoCreateRequest1);
        Todo newTodo2 = todoService.create(todoCreateRequest2);
        Todo newTodo3 = todoService.create(todoCreateRequest3);

        todoService.update(newTodo1.getId(), true);
        todoService.update(newTodo3.getId(), true);

        List<Todo> completed = new ArrayList<>();
        completed.add(newTodo1);
        completed.add(newTodo3);

        assertEquals(completed, todoService.listCompleted());

    }

    @Test
    void testListCompletedTodosWithEmptyList() {

        List<Todo> completed = new ArrayList<>();

        assertEquals(completed, todoService.listCompleted());

    }



    @Test
    void testListCompletedTodosWithNoCompletedTodos() {

        TodoCreateRequest todoCreateRequest1 = new TodoCreateRequest("title1", "description1");
        TodoCreateRequest todoCreateRequest2 = new TodoCreateRequest("title2", "description2");
        TodoCreateRequest todoCreateRequest3 = new TodoCreateRequest("title3", "description3");

        Todo newTodo1 = todoService.create(todoCreateRequest1);
        Todo newTodo2 = todoService.create(todoCreateRequest2);
        Todo newTodo3 = todoService.create(todoCreateRequest3);

        List<Todo> completed = new ArrayList<>();

        assertEquals(completed, todoService.listCompleted());

    }
}
