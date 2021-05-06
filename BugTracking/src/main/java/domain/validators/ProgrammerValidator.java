package domain.validators;

import domain.Programmer;

public class ProgrammerValidator implements Validator<Programmer> {
    @Override
    public void validate(Programmer entity) {
        String err = "";
        try {
            new UserValidator().validate(entity);
        }catch (ValidationException e){err+=e.getMessage();}
        if(err.length()>0)throw new ValidationException(err);
    }
}
