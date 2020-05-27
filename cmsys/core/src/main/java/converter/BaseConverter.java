package converter;

import dto.BaseDTO;
import model.Entity;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BaseConverter<Model extends Entity<Long>, Dto extends BaseDTO> implements converter.Converter<Model, Dto> {
    public List<Long> convertModelsToIDs(Set<Model> models) {
        return models.stream()
                .map(Entity::getId)
                .collect(Collectors.toList());
    }

    public List<Long> convertDTOsToIDs(Set<Dto> dtos) {
        return dtos.stream()
                .map(BaseDTO::getId)
                .collect(Collectors.toList());
    }

    public List<Dto> convertModelsToDtos(Collection<Model> models) {
        return models.stream()
                .map(this::convertModelToDto)
                .collect(Collectors.toList());
    }
}
