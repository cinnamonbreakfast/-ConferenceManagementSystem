package converter;

import dto.BaseDTO;
import model.Entity;

public interface Converter<Model extends Entity<Long>, Dto extends BaseDTO> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}
