    package com.leadgen.backend.Utils;

    import org.modelmapper.ModelMapper;
    import org.springframework.beans.factory.annotation.Autowired;

    import java.lang.reflect.ParameterizedType;

    public abstract class MapperUtil<T, DTO> {

        protected final ModelMapper modelMapper;


        public MapperUtil(ModelMapper modelMapper) {
            this.modelMapper = modelMapper;
        }

        public DTO toDTO(T entity) {
            return modelMapper.map(entity, getDTOClass());
        }

        public T toEntity(DTO dto) {
            return modelMapper.map(dto, getEntityClass());
        }

        @SuppressWarnings("unchecked")
        private Class<DTO> getDTOClass() {
            return (Class<DTO>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        }

        @SuppressWarnings("unchecked")
        private Class<T> getEntityClass() {
            return (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[1];
        }


    }
