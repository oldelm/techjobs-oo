package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view

        Job job = jobData.findById(id);

        model.addAttribute("job", job);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            //use same jobForm so will retain current values:
            model.addAttribute("jobForm", jobForm);
            return "new-job";
        }

        // create new job:
        String myName = jobForm.getName();
        // ^ name that came from template

        Employer myEmployer = jobForm.getEmpById(jobForm.getEmployerId());
        // ^ plug myEmployer into the constructor. Use the helper method
        // jobForm.getEmpById and the "getter" method jobForm.getEmployerId()
        // to get the ID. Then do the same for the other 3 cases:
        Location myLocation = jobForm.getLocById(jobForm.getLocationId());
        PositionType myPosition = jobForm.getPosById(jobForm.getPositionId());
        CoreCompetency myCompetency = jobForm.getCompById(jobForm.getCompetencyId());

        // use constructor to create a new job:
        Job newJob = new Job(myName, myEmployer, myLocation, myPosition, myCompetency);

        // add new Job to the list of existing ones, using global variable jobData:
        jobData.add(newJob);

        return "redirect:/job" + "?id=" + newJob.getId();
        // ^ dont need to convert int to string here b/c java does it for you


        // old return statement:
        //return "";

    }
}
