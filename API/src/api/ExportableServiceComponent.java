package api;

import teamh.api.exceptions.ServiceException;

public interface ExportableServiceComponent {
    void run(int xcoord, int ycoord, int windowWidth, int windowLength, String cssPath, String destNodeID, String originNodeID) throws ServiceException;
}
