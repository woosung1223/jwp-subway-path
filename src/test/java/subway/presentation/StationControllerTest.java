package subway.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import subway.application.service.StationService;
import subway.presentation.controller.StationController;
import subway.presentation.dto.StationRequest;
import subway.presentation.dto.StationResponse;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StationController.class)
public class StationControllerTest {


    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StationService stationService;

    @Test
    @DisplayName("/stations 로 POST 요청을 보낼 시, 생성된 역을 응답한다")
    void createStation() throws Exception {
        // given
        StationRequest stationRequest = new StationRequest("잠실역");
        StationResponse stationResponse = new StationResponse(1L, "잠실역");

        String body = objectMapper.writeValueAsString(stationRequest);
        when(stationService.saveStation(any())).thenReturn(stationResponse);

        // when
        mockMvc.perform(post("/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))

                // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty());
    }

    @Test
    @DisplayName("/stations 로 GET 요청을 보낼 시, 모든 역을 응답한다")
    void showStations() throws Exception {
        // given
        List<StationResponse> stationResponses = List.of(
                new StationResponse(1L, "잠실역"),
                new StationResponse(2L, "방배역")
        );

        when(stationService.findAllStationResponses()).thenReturn(stationResponses);

        // when
        mockMvc.perform(get("/stations"))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].name").isNotEmpty());
    }

    @Test
    @DisplayName("/stations/{id} 로 GET 요청을 보낼 시, 특정 역을 응답한다")
    void showStation() throws Exception {
        // given
        StationResponse stationResponse = new StationResponse(1L, "잠실역");
        when(stationService.findStationResponseById(any())).thenReturn(stationResponse);

        // when
        mockMvc.perform(get("/stations/{id}", 1L))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty());
    }

    @Test
    @DisplayName("/stations/{id} 로 PUT 요청을 보낼 시, 특정 역을 수정한다")
    void updateLine() throws Exception {
        // given
        StationRequest stationRequest = new StationRequest("잠실역");
        String body = objectMapper.writeValueAsString(stationRequest);

        doNothing().when(stationService).updateStation(any(), any());

        // when
        mockMvc.perform(get("/stations/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))

                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("/stations/{id} 로 DELETE 요청을 보낼 시, 특정 노선을 삭제한다")
    void deleteLine() throws Exception {
        // given
        doNothing().when(stationService).deleteStationById(any());

        // when
        mockMvc.perform(delete("/stations/{id}", 1L))

                // then
                .andExpect(status().isNoContent());
    }
}
