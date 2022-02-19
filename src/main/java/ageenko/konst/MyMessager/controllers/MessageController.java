package ageenko.konst.MyMessager.controllers;

import ageenko.konst.MyMessager.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    private int counter = 4;

    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First message"); }});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second message"); }});
        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third message"); }});
    }};

    @GetMapping
    public List<Map<String,String>> list(){
        return messages;
    }



    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id){
        return getMessageById(id);
    }

    public Map<String, String> getMessageById(String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> addOne(@RequestBody Map<String, String> message){
        message.put("id",String.valueOf(counter++));
        messages.add(message);
        return message;
    }

    @PutMapping("/{id}")
    public Map<String,String> update(@PathVariable String id, @RequestBody Map<String, String> message){
        Map <String, String> messageFromDB = getMessageById(id);
        message.putAll(messageFromDB);
        message.put("id", id);
        return message;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        Map<String, String> message = getMessageById(id);
        messages.remove(message);
    }

}
