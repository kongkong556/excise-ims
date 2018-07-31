package th.co.baiwa.buckwaframework.preferences.rest.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.annotations.ApiOperation;
import th.co.baiwa.buckwaframework.common.bean.ResponseData;
import th.co.baiwa.buckwaframework.common.bean.ResponseDataTable;
import th.co.baiwa.buckwaframework.common.constant.DocumentConstants.MODULE_NAME;
import th.co.baiwa.buckwaframework.common.domain.DataTablesPageRequest;
import th.co.baiwa.buckwaframework.preferences.persistence.entity.Message;
import th.co.baiwa.buckwaframework.preferences.service.MessageService;
import th.co.baiwa.buckwaframework.support.ApplicationCache;

@Controller
@RequestMapping("/api/preferences/message")
public class MessageRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageRestController.class);
	
	private final MessageService messageService;
	
	@Autowired
	public MessageRestController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@GetMapping
	@ApiOperation(
		tags = MODULE_NAME.PREFERENCES,
		value = "Get All Message"
	)
	public ResponseEntity<?> getAll() {
		logger.info("getAll");
		
		ResponseData<Map<String, Message>> response = new ResponseData<>();
		response.setData(ApplicationCache.getMessages());
		
		return new ResponseEntity<ResponseData<Map<String, Message>>>(response, HttpStatus.OK);
	}
	
	@GetMapping("search")
	@ApiOperation(
		tags = MODULE_NAME.PREFERENCES,
		value = "Search Message by Criteria"
	)
	public ResponseEntity<?> search(
			@RequestParam(name = "draw") Integer draw,
			@RequestParam(name = "start") Integer start,
			@RequestParam(name = "length") Integer length,
			String messageCode,
			String messageEn,
			String messageTh,
			String messageType) {
		
		Message message = new Message();
		message.setMessageCode(messageCode);
		message.setMessageEn(messageEn);
		message.setMessageTh(messageTh);
		message.setMessageType(messageType);
		logger.info("search by {}", message);
		
		Pageable pageable = new DataTablesPageRequest(start, length);
		
		List<Message> resultList = messageService.getMessageList(message, pageable);
		Integer recordsTotal = messageService.countMessage();
		
		ResponseDataTable<Message> response = new ResponseDataTable<Message>();
		response.setDraw(++draw);
		response.setStart(start);
		response.setLength(length);
		response.setData(resultList);
		response.setRecordsTotal(recordsTotal);
		response.setRecordsFiltered(recordsTotal);
		
		return new ResponseEntity<ResponseDataTable<Message>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(
		tags = MODULE_NAME.PREFERENCES,
		value = "Get Message by Id"
	)
	public ResponseEntity<?> getMessage(@PathVariable("id") long id) {
		logger.info("getMessage [id={}]", id);
		
		Message message = messageService.getMessageById(id);
		ResponseData<Message> response = new ResponseData<Message>();
		response.setData(message);
		return new ResponseEntity<ResponseData<Message>>(response, HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(
		tags = MODULE_NAME.PREFERENCES,
		value = "Create Message"
	)
	public ResponseEntity<?> create(@RequestBody Message message, UriComponentsBuilder ucBuilder) {
		logger.info("create [message={}]", message);
		
		Message newMessage = messageService.insertMessage(message);
		
		ResponseData<Message> response = new ResponseData<Message>();
		response.setData(newMessage);
		
		return new ResponseEntity<ResponseData<Message>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping
	@ApiOperation(
		tags = MODULE_NAME.PREFERENCES,
		value = "Update Message"
	)
	public ResponseEntity<?> update(@RequestBody  Message message, UriComponentsBuilder ucBuilder) {
		logger.info("update [message={}]", message);
		
		messageService.updateMessage(message);
		
		ResponseData<Message> response = new ResponseData<Message>();
		response.setData(message);
		
		return new ResponseEntity<ResponseData<Message>>(response , HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(
		tags = MODULE_NAME.PREFERENCES,
		value = "Delete Message"
	)
	public ResponseEntity<?> delete(@PathVariable("id") List<Long> ids) {
		logger.info("delete [id={}]", StringUtils.collectionToCommaDelimitedString(ids));
		
		messageService.deleteMessage(ids);
		return new ResponseEntity<Message>(HttpStatus.NO_CONTENT);
	}
}
