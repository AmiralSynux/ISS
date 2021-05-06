package domain.validators;

import domain.User;

public class UserValidator implements Validator<User>{
    @Override
    public void validate(User entity) {
        if(entity==null)throw new ValidationException("User can't be null!");
        String err = "";
        if(entity.getUsername()==null || entity.getUsername().length()<3)err+="Invalid username!\n";
        if(entity.getPassword()==null || entity.getPassword().length()<5)err+="Invalid password!";
        if(err.length()!=0)
            throw new ValidationException(err);
    }
}
