package com.fcai.SoftwareTesting;


import com.fcai.SoftwareTesting.todo.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class TodoControllerTests {

    @Autowired
    TodoController todoController;

    MockMvc mockMvc;

    @Mock
    private TodoService todoService;


    public TodoControllerTests() {
    }

    @BeforeEach
    public void setup() {
       todoService = mock(TodoServiceImpl.class);
       todoController = new TodoController(todoService);
       mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
    }

    @Test
    public void testCreate() throws Exception {
        TodoCreateRequest request = new TodoCreateRequest("Test Title", "Test Description");
        Todo todo = new Todo("1", "Test Title", "Test Description", false);

        when(todoService.create(any(TodoCreateRequest.class))).thenReturn(todo);

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Title\", \"description\":\"Test Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.completed").value(false));

    }

    @Test
    void testCreateTodoWithEmptyTitle() throws Exception {

        when(todoService.create(any(TodoCreateRequest.class))).thenThrow(new IllegalArgumentException("Todo title cannot be empty"));

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\", \"description\":\"Test Description\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateTodoWithEmptyDescription() throws Exception {

        when(todoService.create(any(TodoCreateRequest.class))).thenThrow(new IllegalArgumentException("Todo description cannot be empty"));

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Title\", \"description\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateTodoWithNullTodo() throws Exception {

        when(todoService.create(null)).thenThrow(new IllegalArgumentException("Todo cannot be null"));

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }



    //testRead
    @Test
    public void testRead() throws Exception {
        Todo todo = new Todo("1", "Test Title", "Test Description", false);
        when(todoService.read("1")).thenReturn(todo);

        mockMvc.perform(get("/read")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void testReadTodoWithEmptyId() throws Exception {

        when(todoService.read("")).thenThrow(new IllegalArgumentException("Todo id cannot be empty"));

        mockMvc.perform(get("/read")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testReadTodoWithNotFoundId() throws Exception {

        when(todoService.read("1")).thenThrow(new IllegalArgumentException("Todo not found"));

        mockMvc.perform(get("/read")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testReadTodoWithNullId() throws Exception {

        when(todoService.read(null)).thenThrow(new IllegalArgumentException("Todo id cannot be null"));

        mockMvc.perform(get("/read")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateWithValidInput() throws Exception {
        Todo todo = new Todo("1", "Test Title", "Test Description", true);

        when(todoService.update("1", true)).thenReturn(todo);

        mockMvc.perform(put("/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1")
                        .param("completed", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void testUpdateTodoWithEmptyId() throws Exception {

        when(todoService.update("", true)).thenThrow(new IllegalArgumentException("Todo id cannot be empty"));

        mockMvc.perform(put("/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "")
                        .param("completed", "true"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateTodoWithNullId() throws Exception {

        when(todoService.update(null, true)).thenThrow(new IllegalArgumentException("Todo id cannot be null"));

        mockMvc.perform(put("/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("completed", "true"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateTodoWithNotFoundId() throws Exception {

        when(todoService.update("1", true)).thenThrow(new IllegalArgumentException("Todo not found"));

        mockMvc.perform(put("/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1")
                        .param("completed", "true"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteWithValidID() throws Exception {

        mockMvc.perform(delete("/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteTodoWithEmptyId() throws Exception {

       doThrow(new IllegalArgumentException("Todo id cannot be empty")).when(todoService).delete("");

        mockMvc.perform(delete("/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteTodoWithNullId() throws Exception {

        doThrow(new IllegalArgumentException("Todo id cannot be null")).when(todoService).delete(null);

        mockMvc.perform(delete("/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteTodoWithNotFoundId() throws Exception {

        doThrow(new IllegalArgumentException("Todo not found")).when(todoService).delete("1");

        mockMvc.perform(delete("/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testList() throws Exception {

        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo ("1", "Test Title", "Test Description", false));
        todos.add(new Todo ("2", "Test Title", "Test Description", false));

        when(todoService.list()).thenReturn(todos);

        mockMvc.perform(get("/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].title").value("Test Title"))
                .andExpect(jsonPath("$[0].description").value("Test Description"))
                .andExpect(jsonPath("$[0].completed").value(false))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].title").value("Test Title"))
                .andExpect(jsonPath("$[1].description").value("Test Description"))
                .andExpect(jsonPath("$[1].completed").value(false));
    }

    @Test
    void testListWithException() throws Exception{
        when(todoService.list()).thenThrow(new IllegalArgumentException("Todo list cannot be null"));

        mockMvc.perform(get("/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    void testListCompleted() throws Exception {

        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo ("1", "Test Title", "Test Description", true));

        when(todoService.listCompleted()).thenReturn(todos);

        mockMvc.perform(get("/listCompleted")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].title").value("Test Title"))
                .andExpect(jsonPath("$[0].description").value("Test Description"))
                .andExpect(jsonPath("$[0].completed").value(true));
    }

    @Test
    void testListCompletedWithNoCompletedTodos() throws Exception {

        List<Todo> todos = new ArrayList<>();
        when(todoService.listCompleted()).thenReturn(todos);

        mockMvc.perform(get("/listCompleted")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //test list completed when listCompleted throws exception
    @Test
    void testListCompletedWithException() throws Exception{
        when(todoService.listCompleted()).thenThrow(new IllegalArgumentException("Todo list cannot be null"));

        mockMvc.perform(get("/listCompleted")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

}
