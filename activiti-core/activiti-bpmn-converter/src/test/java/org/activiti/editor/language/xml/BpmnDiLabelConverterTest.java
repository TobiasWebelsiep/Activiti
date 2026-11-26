/*
 * Copyright 2010-2026 Hyland Software, Inc. and its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.editor.language.xml;

import static org.assertj.core.api.Assertions.assertThat;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.GraphicInfo;
import org.junit.jupiter.api.Test;

/**
 * Tests that the BPMN DI label bounds of shapes and edges survive an XML round trip.
 */
public class BpmnDiLabelConverterTest extends AbstractConverterTest {

    @Test
    public void convertXMLToModel() throws Exception {
        BpmnModel bpmnModel = readXMLFile();
        validateModel(bpmnModel);
    }

    @Test
    public void convertModelToXML() throws Exception {
        BpmnModel bpmnModel = readXMLFile();
        BpmnModel parsedModel = exportAndReadXMLFile(bpmnModel);
        validateModel(parsedModel);
    }

    protected String getResource() {
        return "bpmndilabels.bpmn";
    }

    private void validateModel(BpmnModel model) {
        assertBounds(model.getGraphicInfo("start"), 100, 100, 36, 36);
        assertBounds(model.getLabelGraphicInfo("start"), 80, 140, 76, 14);
        assertBounds(model.getGraphicInfo("task"), 200, 78, 100, 80);
        assertThat(model.getLabelGraphicInfo("task")).as("label of the task without BPMNLabel").isNull();
        assertThat(model.getLabelGraphicInfo("end")).as("label of the end event without BPMNLabel").isNull();

        assertBounds(model.getLabelGraphicInfo("unnamedFlow"), 143, 95, 50, 14);
        assertBounds(model.getLabelGraphicInfo("namedFlow"), 315, 95, 30, 14);
        assertThat(model.getFlowLocationGraphicInfo("unnamedFlow")).hasSize(2);
    }

    private void assertBounds(GraphicInfo graphicInfo, double x, double y, double width, double height) {
        assertThat(graphicInfo).isNotNull();
        assertThat(graphicInfo.getX()).isEqualTo(x);
        assertThat(graphicInfo.getY()).isEqualTo(y);
        assertThat(graphicInfo.getWidth()).isEqualTo(width);
        assertThat(graphicInfo.getHeight()).isEqualTo(height);
    }
}
