package domain.validators;

import domain.Tester;

public class TesterValidator implements Validator<Tester> {

    public void validate(Tester entity) {
        String err = "";
        try {
            new UserValidator().validate(entity);
        }catch (ValidationException e){err+=e.getMessage();}
        if(err.length()>0)throw new ValidationException(err);
    }
}
