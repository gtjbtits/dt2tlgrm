package com.jbtits.github2telegram.domain.dto.announce;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class MentionAnnounce extends AbstractAnnounce {
  @NonNull
  private final String[] to;

  public MentionAnnounce(long chatId, @NonNull String[] to) {
    super(chatId);
    this.to = to;
  }
}
