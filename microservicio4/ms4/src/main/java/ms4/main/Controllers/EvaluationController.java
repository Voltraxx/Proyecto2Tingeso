package ms4.main.Controllers;


import ms4.main.Services.EvaluationService;
import ms4.main.dtos.EvaluationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping("/evaluate/{rule}")
    public ResponseEntity<Boolean> evaluateRule(@RequestBody EvaluationDTO evaluationDTO, @PathVariable String rule) {
        try {
            boolean result = evaluationService.evaluateRule(evaluationDTO, rule);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
