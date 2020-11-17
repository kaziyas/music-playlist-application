package com.mondiamedia.app.shared;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mondiamedia.app.service.shared.IdGeneratorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.30
 * @since 1.0
 */
@SpringBootTest
class IdGeneratorUtilsTest {

  @BeforeEach
  void setUp() throws Exception {}

  @Test
  final void testGenerateplaylistId() {
    String playlistId = IdGeneratorUtils.generatePlaylistId(30);
    String playlistId2 = IdGeneratorUtils.generatePlaylistId(30);

    assertNotNull(playlistId);
    assertNotNull(playlistId2);

    assertTrue(playlistId.length() == 30);
    assertTrue(!playlistId.equalsIgnoreCase(playlistId2));
  }
}
