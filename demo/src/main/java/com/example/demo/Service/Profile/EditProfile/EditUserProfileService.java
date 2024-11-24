package com.example.demo.Service.Profile.EditProfile;

import com.example.demo.Entity.GeneralUser;

public interface EditUserProfileService {
    GeneralUser editProfile(int id, GeneralUser updatedProfile);
}
