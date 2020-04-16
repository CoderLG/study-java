package lg.dto;

import lombok.Data;

/**
 * author: LG
 * date: 2019-09-21 17:00
 * desc:
 */
@Data
public class BaseDto<T> {
    T data;
}
