package simsimbooks.couponserver.common.util;

public class DtoMapper {
    public static <T, U> T toDto(U entity, Class<T> tClass) {
        try {
            return tClass.getConstructor(entity.getClass()).newInstance(entity);
        } catch (Exception e) {
            throw new IllegalArgumentException("DTO 변환에 실패했습니다.");
        }
    }
}
