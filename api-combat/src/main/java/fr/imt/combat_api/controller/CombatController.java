package fr.imt.combat_api.controller;

import fr.imt.combat_api.model.Combat;
import fr.imt.combat_api.service.CombatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/combat")
public class CombatController {
}
