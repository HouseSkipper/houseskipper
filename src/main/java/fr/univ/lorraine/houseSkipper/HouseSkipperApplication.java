package fr.univ.lorraine.houseSkipper;

import fr.univ.lorraine.houseSkipper.configuration.FileStorageProperties;
import fr.univ.lorraine.houseSkipper.model.Phase;
import fr.univ.lorraine.houseSkipper.model.SubPhase;
import fr.univ.lorraine.houseSkipper.repositories.PhaseRepository;
import fr.univ.lorraine.houseSkipper.repositories.SubPhaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
@EnableWebSecurity
public class HouseSkipperApplication {

	@Autowired
	private PhaseRepository phaseRepository;

	@Autowired
	private SubPhaseRepository subPhaseRepository;



	@Bean
	@Primary
	public void initDB(){
		try{
			phaseRepository.findAll();
			subPhaseRepository.findAll();
		}catch (Exception e) {
			phaseRepository.deleteAll();
			subPhaseRepository.deleteAll();
			String[] phases = {"Phase Rédaction", "Phase Validation", "Phase Soumission", "Phase Evaluation",
					"Phase Décision", "Phase Finalisation", "Phase Exploitation"};
			for (int i = 0; i < phases.length; i++){
				phaseRepository.save(new Phase(new Long(i), phases[i]));
			}

			for (Phase p:
					phaseRepository.findAll()) {
				switch (p.getPhaseName()){
					case "Phase Rédaction":
						SubPhase s1 = new SubPhase(new Long(1),"Description en cours");
						SubPhase s2 = new SubPhase(new Long(2),"Description finalisée");
						List<SubPhase> sub = new ArrayList<>();
						sub.add(s1);
						sub.add(s2);
						phaseRepository.findById(new Long(1)).get().setSubPhase(sub);
						break;
					case "Phase Validation":
						SubPhase s3 = new SubPhase(new Long(3),"Description en cours de relecture");
						SubPhase s4 = new SubPhase(new Long(4),"Description à compléter");
						SubPhase s5 = new SubPhase(new Long(5),"Description à validée");
						List<SubPhase> sub1 = new ArrayList<>();
						sub1.add(s3);
						sub1.add(s4);
						sub1.add(s5);
						phaseRepository.findById(new Long(2)).get().setSubPhase(sub1);
						break;
					case "Phase Soumission":
						SubPhase s6 = new SubPhase(new Long(6),"En attente de réponse");
						SubPhase s7 = new SubPhase(new Long(7),"Réponses en cours");
						SubPhase s8 = new SubPhase(new Long(8),"Réponses clôturées");
						List<SubPhase> sub2 = new ArrayList<>();
						sub2.add(s6);
						sub2.add(s7);
						sub2.add(s8);
						phaseRepository.findById(new Long(3)).get().setSubPhase(sub2);
						break;
					case "Phase Evaluation":
						SubPhase s9 = new SubPhase(new Long(4),"Description en cours");
						SubPhase s10 = new SubPhase(new Long(5),"Description finalisée");
						List<SubPhase> sub3 = new ArrayList<>();
						sub3.add(s9);
						sub3.add(s10);
						phaseRepository.findById(new Long(4)).get().setSubPhase(sub3);
						break;
					case "Phase Décision":
						SubPhase s11 = new SubPhase(new Long(11),"Abandon");
						SubPhase s12 = new SubPhase(new Long(12),"Suspendu");
						SubPhase s13 = new SubPhase(new Long(13),"Réalisation");
						List<SubPhase> sub4 = new ArrayList<>();
						sub4.add(s11);
						sub4.add(s12);
						sub4.add(s13);
						phaseRepository.findById(new Long(5)).get().setSubPhase(sub4);
						break;
					case "Phase Finalisation":
						SubPhase s14 = new SubPhase(new Long(14),"Vérification en cours");
						SubPhase s15 = new SubPhase(new Long(15),"En attente de complément");
						SubPhase s16 = new SubPhase(new Long(16),"Vérification finalisée");
						SubPhase s17 = new SubPhase(new Long(17),"En attente d’exploitation");
						List<SubPhase> sub5 = new ArrayList<>();
						sub5.add(s14);
						sub5.add(s15);
						sub5.add(s16);
						sub5.add(s17);
						phaseRepository.findById(new Long(6)).get().setSubPhase(sub5);
						break;
					case "Phase Exploitation":
						SubPhase s18 = new SubPhase(new Long(18),"En attente d’exploitation");
						SubPhase s19 = new SubPhase(new Long(19),"En cours d’exploitation");
						List<SubPhase> sub6 = new ArrayList<>();
						sub6.add(s18);
						sub6.add(s19);
						phaseRepository.findById(new Long(7)).get().setSubPhase(sub6);
						break;
				}

			}
		}

	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(HouseSkipperApplication.class, args);

	}



}
