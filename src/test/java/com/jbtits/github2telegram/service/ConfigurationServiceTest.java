package com.jbtits.github2telegram.service;

import com.jbtits.github2telegram.domain.dto.entity.FellowRequest;
import com.jbtits.github2telegram.domain.dto.entity.TeamRequest;
import com.jbtits.github2telegram.domain.dto.entity.TribeRequest;
import com.jbtits.github2telegram.service.impl.DefaultConfigurationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationServiceTest {
	
	private static ConfigurationService configurationService;
	
	@BeforeAll
	static void setUp() {
		configurationService = new DefaultConfigurationService();
	}
	
	@Test
	void build_Must_ReturnCreatingOnlyConfigurationIfNoTribeInfoHasBeenPersistedBefore() {
		final TribeRequest tribeRequest = new TribeRequest();
		
		final TeamRequest teamRequest = new TeamRequest();
		
		final FellowRequest fellowRequest1 = new FellowRequest();
		final FellowRequest fellowRequest2 = new FellowRequest();
		
		teamRequest.getFellows().addAll(Set.of(fellowRequest1, fellowRequest2));
		tribeRequest.getTeams().add(teamRequest);
		
		final var config = configurationService.build(tribeRequest);
		assertThat(config).isNotNull();
		assertThat(config.getCreating()).isEqualTo(Set.of(tribeRequest));
		assertThat(config.getUpdating()).isEmpty();
		assertThat(config.getDeleting()).isEmpty();
		
		final var teamConfig = config.getTeamConfiguration();
		assertThat(teamConfig).isNotNull();
		assertThat(teamConfig.getCreating()).isEqualTo(Set.of(teamRequest));
		assertThat(teamConfig.getUpdating()).isEmpty();
		assertThat(teamConfig.getDeleting()).isEmpty();
		
		final var fellowConfig = teamConfig.getFellowConfiguration();
		assertThat(fellowConfig).isNotNull();
		assertThat(fellowConfig.getCreating()).isEqualTo(Set.of(fellowRequest1, fellowRequest2));
		assertThat(fellowConfig.getUpdating()).isEmpty();
		assertThat(fellowConfig.getDeleting()).isEmpty();
	}
}