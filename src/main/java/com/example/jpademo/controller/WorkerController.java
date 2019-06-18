package com.example.jpademo.controller;

import com.example.jpademo.controller.util.HeaderUtil;
import com.example.jpademo.controller.util.PaginationUtil;
import com.example.jpademo.service.WorkerService;
import com.example.jpademo.domain.Worker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api
@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    private final Logger log = LoggerFactory.getLogger(WorkerController.class);

    private String ENTITY_NAME = "Worker";

    private WorkerService workerService;

    @Autowired
    private RedisTemplate<Serializable, Object> redisTemplate;

    @Autowired
    public void setWorkerService(WorkerService workerService) {
        this.workerService = workerService;
    }

    @ApiOperation("查看教练列表")
    @ApiImplicitParam(name = "page",value = "页码,每页5个成员")
    @GetMapping("/index")
    public ResponseEntity<List<Worker>> getAllWorkers(@RequestParam(name = "page", defaultValue = "1") int page) {
        log.debug("REST request to get a page of Employees");
        Pageable pageable=new PageRequest(page,5);
        Page<Worker> workers;
        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES);

//        if (redisTemplate.opsForValue().get("worker_" + pageable.getPageNumber()) == null) {
            workers = workerService.getAll(pageable);
//            redisTemplate.opsForValue().set("worker_"+pageable.getPageNumber(),workers);
 //       } else {
//            workers  = (Page<Worker>) redisTemplate.opsForValue().get("worker_"+pageable.getPageNumber());
 //       }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(workers, "/api/workers");
        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .headers(headers).
                body(workers.getContent());
    }

    @ApiOperation("添加教练信息")
    @PostMapping(value = "/add")
    public ResponseEntity<Worker> addWorker(@RequestBody Worker worker, BindingResult bindingResult) throws URISyntaxException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createAlert("Input message has errors.", worker.getId().toString()))
                    .build();
        }

        this.workerService.addWorker(worker);
        return ResponseEntity.created(new URI("/api/workers/view/" + worker.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, worker.getId().toString()))
                .body(worker);
    }

//    @RequestMapping(value = "/edit/{id}")
//    public String editWorker(@PathVariable Integer id, Model model) {
//        model.addAttribute("worker", workerService.getById(id));
//        model.addAttribute("activePage", "workers");
//        return "workerCrud/edit";
//    }

    @ApiOperation("更新教练信息")
    @PostMapping("/update")
    public ResponseEntity<Worker> updateWorker(@RequestBody Worker worker) throws URISyntaxException {
        log.debug("Rest to update workers.");
        workerService.updateWorker(worker);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, worker.getId().toString()))
                .body(worker);
    }

    @ApiOperation("通过Id查看教练信息")
    @ApiImplicitParam(name = "id",value = "id")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ResponseEntity<Worker> viewWorker(@PathVariable Integer id) {
        if (workerService.getById(id) == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("This worker is not existed.", id.toString())).build();
        }
        return ResponseEntity.ok().body(workerService.getById(id));
    }

    @ApiOperation("删除教练信息")
    @ApiImplicitParam(name = "page",value = "页码,每页5个成员")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<Void> deleteWorker(@PathVariable Integer id) {
        workerService.deleteWorker(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
