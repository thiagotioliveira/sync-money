package dev.thiagooliveira.syncmoney.application.support.page.domain.dto;

public record Pageable(int pageNumber, int pageSize) {
  public static Pageable of(int pageNumber, int pageSize) {
    return new Pageable(pageNumber, pageSize);
  }
}
