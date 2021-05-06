package domain.validators;

import domain.Bug;

public class BugValidator implements Validator<Bug>{
    @Override
    public void validate(Bug entity) {
        if(entity==null)throw new ValidationException("Bug can't be null!");
        String err = "";
        if(entity.getName()==null || entity.getName().length()<3)err+="Invalid name!\n";
        if(entity.getDescription()==null || entity.getDescription().length()<3)err+="Invalid description!\n";
        if(entity.getStatus()==null)err+="Invalid status!";
        if(err.length()!=0)
            throw new ValidationException(err);
    }
}
