package com.example.atc.domain.trashBin.service;

import com.example.atc.domain.trashBin.entity.SelectedTrashBin;
import com.example.atc.domain.trashBin.entity.TrashBin;
import com.example.atc.domain.trashBin.repository.SelectedTrashBinRepository;
import com.example.atc.domain.trashBin.repository.TrashBinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TrashBinService {

    @Autowired
    private TrashBinRepository trashBinRepository;

    @Autowired
    private SelectedTrashBinRepository selectedTrashBinRepository;
    public void saveTrashBinsFromCsv(String filePath) throws IOException {
        List<TrashBin> trashBins = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = line.split(",");

                if (values.length < 2 || values[0].isEmpty() || values[1].isEmpty()) {
                    continue;
                }

                double longitude = Double.parseDouble(values[0]);
                double latitude = Double.parseDouble(values[1]);
                TrashBin trashBin = new TrashBin(latitude, longitude);
                trashBins.add(trashBin);
            }
        }

        trashBinRepository.saveAll(trashBins);
    }

    public List<TrashBin> findTrashBinsWithinRange(double startLat, double startLong, double endLat, double endLong) {
        List<TrashBin> bins = trashBinRepository.findBinsInArea(startLat, startLong, endLat, endLong);
        if (bins.isEmpty()) {
            return List.of();
        }
        Collections.shuffle(bins);
        List<TrashBin> selectedBins = bins.size() > 2 ? bins.subList(0, 2) : bins;


        selectedTrashBinRepository.deleteAll();

        for (TrashBin bin : selectedBins) {
            SelectedTrashBin selectedTrashBin = new SelectedTrashBin(bin.getLatitude(), bin.getLongitude());
            selectedTrashBinRepository.save(selectedTrashBin);
        }
        return selectedBins;
    }

    public TrashBin saveTrashBin(TrashBin trashBin) {
        return trashBinRepository.save(trashBin);
    }

    public List<TrashBin> getAllTrashBins() {
        return trashBinRepository.findAll();
    }
}

