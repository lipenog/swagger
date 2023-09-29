package com.example.swaggerguide.entity;

import com.example.swagger_guide.api.model.TaskStatus;
import jakarta.persistence.*;

import javax.validation.constraints.NotNull;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private TaskStatus status;
    @ManyToOne
    private Member createdBy;
    @ManyToOne
    @JoinColumn
    private Member workingOn;

    public Task(){

    }

    public Task(String name, String description, TaskStatus status, Member createdBy, Member workingOn) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
        this.workingOn = workingOn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Member getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Member createdBy) {
        this.createdBy = createdBy;
    }

    public Member getWorkingOn() {
        return workingOn;
    }

    public void setWorkingOn(Member workingOn) {
        this.workingOn = workingOn;
    }
}
