package org.odk.collect.android.widgets.warnings;

import com.google.common.collect.Lists;
import org.javarosa.core.model.SelectChoice;
import org.junit.Before;
import org.junit.Test;
import org.odk.collect.android.widgets.warnings.SpacesInUnderlyingValuesWarning.SpacesInUnderlyingValues;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.odk.collect.android.widgets.warnings.SpacesInUnderlyingValuesWarning.SpacesInUnderlyingValuesImpl;

public class SpacesInUnderlyingValuesTest {

    private SpacesInUnderlyingValues subject;

    @Before
    public void setUp() {
        subject = new SpacesInUnderlyingValues();
    }

    @Test
    public void doesNotDetectErrorWhenThereIsNone() {
        List<SelectChoice> items = Lists.newArrayList(
                new SelectChoice("label", "no_space")
        );

        subject.check(items);
        assertEquals(subject.hasInvalidValues(), false);
    }

    @Test
    public void doesNotDetectErrorInEmptySet() {
        List<SelectChoice> items = Lists.newArrayList();

        subject.check(items);
        assertEquals(subject.hasInvalidValues(), false);
    }

    @Test
    public void doesDetectSingleSpaceError() {
        List<SelectChoice> items = Lists.newArrayList(
                new SelectChoice("label", "with space")
        );

        subject.check(items);
        assertEquals(subject.hasInvalidValues(), true);
    }

    @Test
    public void detectsMultipleErrors() {
        List<SelectChoice> items = Lists.newArrayList(
                new SelectChoice("label", "with space"),
                new SelectChoice("label2", "with space2")
        );

        subject.check(items);
        assertEquals(subject.hasInvalidValues(), true);
    }

    @Test
    public void returnsInvalidValues() {
        List<SelectChoice> items = Lists.newArrayList(
                new SelectChoice("label", "with space"),
                new SelectChoice("label2", "with space2")
        );

        subject.check(items);
        assertEquals(subject.getInvalidValues().size(), 2);
    }

    @Test
    public void detectsSpaceInTheBeginningOfUnderlyingValue() {
        List<SelectChoice> items = Lists.newArrayList(
                new SelectChoice("label", " before")
        );

        subject.check(items);
        assertEquals(subject.hasInvalidValues(), true);
    }

    @Test
    public void detectsSpaceInTheEndOfUnderlyingValue() {
        List<SelectChoice> items = Lists.newArrayList(
                new SelectChoice("label", "after ")
        );

        subject.check(items);
        assertEquals(subject.hasInvalidValues(), true);
    }
}