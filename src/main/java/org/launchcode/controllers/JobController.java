package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobData;
import org.launchcode.models.forms.JobForm;
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
    @RequestMapping(value = "/", method = RequestMethod.GET)
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
        if (errors.hasErrors()){
            model.addAttribute(errors);
            return "new-job";
        }
        String aName = jobForm.getName();
        int empId = jobForm.getEmployerId();
        Employer newEmployer = jobData.getEmployers().findById(empId);
        int locId = jobForm.getLocationId();
        Location newLocation = jobData.getLocations().findById(locId);
        int coreCompId = jobForm.getCoreCompetencyId();
        CoreCompetency newCoreComp = jobData.getCoreCompetencies().findById(coreCompId);
        int posTypeId = jobForm.getPositionTypeId();
        PositionType newPosType = jobData.getPositionTypes().findById(posTypeId);
        Job newJob =  new Job(aName, newEmployer, newLocation, newPosType, newCoreComp);
        jobData.add(newJob);

        return "redirect:?id=" + newJob.getId();

    }
}
