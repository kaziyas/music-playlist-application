package com.mondiamedia.app.service.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.30
 * @since 1.0
 */
class IdGeneratorUtilsTest {

  @Test
  final void testGeneratePlaylistId() {
    String playlistId = IdGeneratorUtil.generatePlaylistId(30);
    String playlistId2 = IdGeneratorUtil.generatePlaylistId(30);

    assertNotNull(playlistId);
    assertNotNull(playlistId2);

    assertEquals(30, playlistId.length());
    assertFalse(playlistId.equalsIgnoreCase(playlistId2));
  }
}
